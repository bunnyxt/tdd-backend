package com.bunnyxt.tdd.legacy;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.util.*;

/** Reads the original sprint tables, with bound parameters and no implicit history cap. */
@Repository
public class LegacyRepository {
    private static final Logger LOG = LoggerFactory.getLogger(LegacyRepository.class);
    private final JdbcTemplate jdbc;

    public LegacyRepository(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
        jdbc.setQueryTimeout(300);
    }

    private List<Map<String, Object>> query(String sql, List<Object> args, String numbers,
                                           String strings, boolean reverse) {
        List<Map<String, Object>> rows;
        try {
            rows = jdbc.query(sql, args.toArray(), (rs, index) -> {
            Map<String, Object> row = new LinkedHashMap<>();
            for (String key : numbers.split(" ")) {
                if (!key.isEmpty()) row.put(key, LegacyParameters.integer(rs.getString(key)));
            }
            for (String key : strings.split(" ")) {
                if (!key.isEmpty()) row.put(key, rs.getString(key));
            }
            return row;
            });
        } catch (CannotGetJdbcConnectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            // The original PDO connection uses ERRMODE_SILENT: failed queries yield [].
            LOG.warn("legacy_query_failed route={}", MDC.get("legacy_route"));
            return new ArrayList<>();
        }
        if (reverse) Collections.reverse(rows);
        return rows;
    }

    private String limit(String sql, List<Object> args, long limit) {
        if (limit > 0) { args.add(limit); return sql + " LIMIT ?"; }
        return sql;
    }

    public List<Map<String, Object>> members(long mid, long limit) {
        List<Object> args = new ArrayList<>();
        String sql = "SELECT id,mid,sex,name,face FROM tdd_member WHERE 1=1";
        if (mid != -1) { sql += " AND mid=?"; args.add(mid); }
        return query(limit(sql, args, limit), args, "id mid", "sex name face", false);
    }

    public List<Map<String, Object>> daily(long date, long limit) {
        List<Object> args = new ArrayList<>();
        String sql = "SELECT id,date,correct,vidnum,newvids,millvids,viewincr,viewincrincr,comment FROM tdd_sprint_daily WHERE 1=1";
        if (date != -1) { sql += " AND date=?"; args.add(date); }
        List<Map<String, Object>> rows = query(limit(sql + " ORDER BY id DESC", args, limit), args,
                "id date correct vidnum viewincr viewincrincr", "newvids millvids comment", true);
        for (Map<String, Object> row : rows) {
            row.put("newvids", numberTokens((String) row.get("newvids")));
            row.put("millvids", numberTokens((String) row.get("millvids")));
        }
        return rows;
    }

    private List<Long> numberTokens(String value) {
        String[] tokens = (value == null ? "" : value).split(";", -1);
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) result.add(LegacyParameters.integer(tokens[i]));
        return result;
    }

    public List<Map<String, Object>> dailyRecords(long date, long limit) {
        List<Object> args = new ArrayList<>();
        args.add(date);
        String sql = "SELECT id,date,aid,view,viewincr,pday,lday FROM tdd_sprint_daily_record WHERE date=? ORDER BY id DESC";
        return query(limit(sql, args, limit), args, "id aid view viewincr pday lday", "date", true);
    }

    public List<Map<String, Object>> videos(long aid, String status, long limit) {
        List<Object> args = new ArrayList<>();
        String sql = "SELECT id,mid,aid,title,pic,created,singer,solo,original,status FROM tdd_sprint_video WHERE 1=1";
        if (aid != -1) { sql += " AND aid=?"; args.add(aid); }
        if (!"all".equals(status)) { sql += " AND status=?"; args.add(status); }
        List<Map<String, Object>> rows = query(limit(sql, args, limit), args,
                "id mid aid created solo original", "title pic singer status", false);
        for (Map<String, Object> row : rows) {
            String singer = (String) row.get("singer");
            String[] tokens = (singer == null ? "" : singer).split(",", -1);
            row.put("singer", tokens.length == 1 ? null :
                    new ArrayList<>(Arrays.asList(tokens).subList(0, tokens.length - 1)));
        }
        return rows;
    }

    public List<Map<String, Object>> records(long aid, long start, long end, long limit) {
        List<Object> args = new ArrayList<>();
        String sql = "SELECT id,added,view FROM tdd_sprint_video_record WHERE 1=1";
        if (aid != -1) { sql += " AND aid=?"; args.add(aid); }
        if (start > 0) { sql += " AND added>=?"; args.add(start); }
        if (end > 0) { sql += " AND added<?"; args.add(end); }
        return query(limit(sql + " ORDER BY id DESC", args, limit), args, "id added view", "", true);
    }

    @FunctionalInterface
    public interface RecordConsumer {
        void accept(long id, long added, long view) throws IOException;
    }

    /** Reverse the selected DESC/LIMIT set in SQL, then consume without buffering in JDBC. */
    public void streamRecords(long aid, long start, long end, long limit, RecordConsumer consumer)
            throws IOException {
        List<Object> args = new ArrayList<>();
        args.add(aid);
        String base = "SELECT id,added,view FROM tdd_sprint_video_record WHERE aid=?";
        if (start > 0) { base += " AND added>=?"; args.add(start); }
        if (end > 0) { base += " AND added<?"; args.add(end); }
        final String sql = limit > 0
                ? "SELECT id,added,view FROM (" + limit(base + " ORDER BY id DESC", args, limit)
                    + ") selected_records ORDER BY id ASC"
                : base + " ORDER BY id ASC";
        final IOException[] failure = {null};
        final long[] count = {0};
        try {
            jdbc.execute((ConnectionCallback<Void>) connection -> {
                try (PreparedStatement statement = connection.prepareStatement(sql,
                        ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
                    statement.setQueryTimeout(300);
                    // Connector/J's forward-only streaming mode. H2 is used only by tests.
                    if (connection.getMetaData().getDatabaseProductName().equals("MySQL"))
                        statement.setFetchSize(Integer.MIN_VALUE);
                    for (int i = 0; i < args.size(); i++) statement.setObject(i + 1, args.get(i));
                    try (ResultSet rows = statement.executeQuery()) {
                        while (rows.next()) {
                            try {
                                consumer.accept(rows.getLong("id"), rows.getLong("added"), rows.getLong("view"));
                                count[0]++;
                            } catch (IOException ex) { failure[0] = ex; break; }
                        }
                    }
                }
                return null;
            });
        } catch (CannotGetJdbcConnectionException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            LOG.warn("legacy_query_failed route={}", MDC.get("legacy_route"));
            if (count[0] != 0) throw ex; // Never deliver a silently truncated history.
        }
        if (failure[0] != null) throw failure[0];
    }
}
