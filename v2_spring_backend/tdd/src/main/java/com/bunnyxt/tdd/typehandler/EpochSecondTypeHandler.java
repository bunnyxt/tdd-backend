package com.bunnyxt.tdd.typehandler;

import com.bunnyxt.tdd.util.EpochSeconds;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

/** Explicit timestamp parameter binding; ordinary bigint Long columns are unchanged. */
public class EpochSecondTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement statement, int index, Long value, JdbcType jdbcType)
            throws SQLException {
        statement.setObject(index, EpochSeconds.utcDateTime(value));
    }

    @Override
    public Long getNullableResult(ResultSet rows, String column) throws SQLException {
        return epoch(rows.getObject(column, OffsetDateTime.class));
    }

    @Override
    public Long getNullableResult(ResultSet rows, int column) throws SQLException {
        return epoch(rows.getObject(column, OffsetDateTime.class));
    }

    @Override
    public Long getNullableResult(CallableStatement statement, int column) throws SQLException {
        return epoch(statement.getObject(column, OffsetDateTime.class));
    }

    private Long epoch(OffsetDateTime value) {
        return value == null ? null : value.toEpochSecond();
    }
}
