package com.bunnyxt.tdd.legacy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Wire DTO for the PHP compatibility API; deliberately separate from v2 models. */
public final class LegacyResponse {
    public int status;
    public Map<String, Object> meta = new LinkedHashMap<>();
    public List<Map<String, Object>> data;

    public static LegacyResponse ok(List<Map<String, Object>> data) {
        LegacyResponse response = new LegacyResponse();
        response.status = 200;
        response.meta.put("count", data.size());
        response.data = data;
        return response;
    }

    public static LegacyResponse invalidAid() {
        LegacyResponse response = new LegacyResponse();
        response.status = 400;
        response.meta.put("message", "\u5fc5\u987b\u63d0\u4f9baid\u5e76\u4e14aid\u4e3a\u6b63\u6574\u6570");
        return response;
    }
}
