package com.bunnyxt.tdd.legacy;

import org.springframework.stereotype.Service;
import java.util.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LegacyService {
    private final LegacyRepository repository;

    public LegacyService(LegacyRepository repository) { this.repository = repository; }

    public Object member(LegacyParameters p) {
        long mid = p.number("mid", -1);
        return mid == -1 ? error("Must provide mid!") :
                LegacyResponse.ok(repository.members(mid, p.number("limit", 0)));
    }

    public LegacyResponse daily(LegacyParameters p) {
        return LegacyResponse.ok(repository.daily(p.number("date", -1), p.number("limit", 0)));
    }

    public Object dailyRecords(LegacyParameters p) {
        long date = p.number("date", -1);
        if (date == -1) return error("Must provide date!");
        List<Map<String, Object>> rows = repository.dailyRecords(date, p.number("limit", 0));
        for (Map<String, Object> row : rows) {
            Map<String, Object> video = first(repository.videos(number(row, "aid"), "all", 0));
            if (video != null) {
                video.remove("status");
                video.put("member", first(repository.members(number(video, "mid"), 0)));
            }
            if (video == null) {
                video = new LinkedHashMap<>();
                video.put("member", null); // PHP creates an object when assigning to a missing video.
            }
            row.put("sprint_video", video);
        }
        return LegacyResponse.ok(rows);
    }

    public LegacyResponse videos(LegacyParameters p) {
        List<Map<String, Object>> rows = repository.videos(p.number("aid", -1),
                p.string("status", "all"), p.number("limit", 0));
        for (Map<String, Object> row : rows) {
            long aid = number(row, "aid");
            row.put("member", first(repository.members(number(row, "mid"), 0)));
            Map<String, Object> latest = first(repository.records(aid, 0, 0, 1));
            row.put("last_record", latest);
            long cutoff = latest == null ? -86400 : number(latest, "added") - 86400;
            Map<String, Object> before = first(repository.records(aid, 0, cutoff, 1));
            row.put("one_day_view", before == null ? -1L :
                    (latest == null ? 0 : number(latest, "view")) - number(before, "view"));
        }
        return LegacyResponse.ok(rows);
    }

    public Path recordsFile(LegacyParameters p) throws IOException {
        long aid = p.number("aid", -1);
        Path file = Files.createTempFile("tdd-legacy-history-", ".json");
        boolean success = false;
        try {
            try (JsonGenerator out = new JsonFactory().createGenerator(Files.newOutputStream(file))) {
                out.writeStartObject();
                out.writeNumberField("status", 200);
                out.writeArrayFieldStart("data");
                final long[] count = {0};
                repository.streamRecords(aid, p.number("start", 0), p.number("end", 0), p.number("limit", 0),
                        (id, added, view) -> {
                            out.writeStartObject();
                            out.writeNumberField("id", id);
                            out.writeNumberField("added", added);
                            out.writeNumberField("view", view);
                            out.writeEndObject();
                            count[0]++;
                        });
                out.writeEndArray();
                out.writeObjectFieldStart("meta");
                out.writeNumberField("aid", aid);
                out.writeNumberField("count", count[0]);
                out.writeEndObject();
                out.writeEndObject();
            }
            success = true;
            return file;
        } finally { if (!success) Files.deleteIfExists(file); }
    }

    private static Map<String, Object> first(List<Map<String, Object>> rows) {
        return rows.isEmpty() ? null : rows.get(0);
    }

    private static long number(Map<String, Object> row, String field) {
        return ((Number) row.get(field)).longValue();
    }

    private static Map<String, Object> error(String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", 400);
        result.put("message", message);
        return result;
    }
}
