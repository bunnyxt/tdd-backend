package com.bunnyxt.tdd.legacy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("/legacy")
public class LegacyController {
    private static final Logger LOG = LoggerFactory.getLogger(LegacyController.class);
    private final LegacyService service;
    private final LegacyBiliClient bili;
    // Keep compatibility serialization independent of global v2 Jackson customizations.
    private final ObjectMapper json = new ObjectMapper();

    public LegacyController(LegacyService service, LegacyBiliClient bili) {
        this.service = service;
        this.bili = bili;
    }

    @RequestMapping(value = {"/member", "/sprint_daily", "/sprint_daily_record", "/sprint_video",
            "/sprint_video_record", "/bapi_aid_bvid", "/bapi_view"},
            method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST, RequestMethod.OPTIONS,
                    RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long started = System.nanoTime();
        String route = request.getServletPath().substring("/legacy/".length());
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        int businessStatus = 200;
        MDC.put("legacy_route", route);
        try {
            LegacyParameters p = new LegacyParameters(request.getQueryString());
            Object result;
            switch (route) {
                case "member": result = service.member(p); break;
                case "sprint_daily": result = service.daily(p); break;
                case "sprint_daily_record": result = service.dailyRecords(p); break;
                case "sprint_video": result = service.videos(p); break;
                case "sprint_video_record":
                    if (p.number("aid", -1) <= 0) { result = LegacyResponse.invalidAid(); break; }
                    // Spool to a private temporary file: bounded heap, and the database
                    // connection is released before a slow client reads the full response.
                    Path file = service.recordsFile(p);
                    try { Files.copy(file, response.getOutputStream()); }
                    finally { Files.deleteIfExists(file); }
                    return;
                case "bapi_aid_bvid": result = convert(p); break;
                case "bapi_view":
                    response.getWriter().write(upstream("aid", p.string("aid", "-1")));
                    return;
                default: response.setStatus(404); return;
            }
            if (result instanceof LegacyResponse) businessStatus = ((LegacyResponse) result).status;
            else if (result instanceof Map && ((Map<?, ?>) result).get("status") instanceof Number)
                businessStatus = ((Number) ((Map<?, ?>) result).get("status")).intValue();
            response.getWriter().write(json.writeValueAsString(result));
        } catch (RuntimeException ex) {
            // Do not leak SQL, bound parameters or upstream content into logs or the wire.
            response.setStatus(500);
            businessStatus = 500;
            LOG.warn("legacy_request_failed route={}", route);
        } catch (IOException ex) {
            businessStatus = 500;
            if (!response.isCommitted()) response.setStatus(500);
            LOG.warn("legacy_response_failed route={}", route);
            throw ex;
        } finally {
            LOG.info("legacy_request route={} http_status={} business_status={} duration_ms={}",
                    route, response.getStatus(), businessStatus, (System.nanoTime() - started) / 1000000);
            MDC.remove("legacy_route");
        }
    }

    private String upstream(String key, String value) {
        try { return bili.view(key, value); }
        catch (IOException ex) {
            LOG.warn("legacy_upstream_failed route={}", MDC.get("legacy_route"));
            return ""; // PHP echo(false) on transport failure.
        }
    }

    private Map<String, Object> convert(LegacyParameters p) throws IOException {
        String aid = p.string("aid", "-1");
        String bvid = p.string("bvid", "-1");
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "error");
        result.put("aid", LegacyParameters.integer(aid));
        result.put("bvid", bvid);
        if ("-1".equals(aid) && "-1".equals(bvid)) return result;
        String body = !"-1".equals(aid) ? upstream("aid", aid) : upstream("bvid", bvid);
        JsonNode root;
        try { root = json.readTree(body); }
        catch (IOException ex) { root = null; }
        if (root == null || !root.has("code") || root.get("code").asInt() == 0) {
            result.put("status", "success");
            if (!"-1".equals(aid)) result.put("bvid", root == null ? "" : root.path("data").path("bvid").asText());
            else result.put("aid", root == null ? null : root.path("data").path("aid").numberValue());
        }
        return result;
    }
}
