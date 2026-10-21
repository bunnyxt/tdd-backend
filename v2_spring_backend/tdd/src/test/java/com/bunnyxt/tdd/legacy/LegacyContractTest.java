package com.bunnyxt.tdd.legacy;

import com.bunnyxt.tdd.Application;
import com.fasterxml.jackson.databind.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import javax.sql.DataSource;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:legacy;MODE=MySQL;DB_CLOSE_DELAY=-1",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa", "spring.datasource.password=",
                "logging.level.root=WARN"})
public class LegacyContractTest {
    @LocalServerPort private int port;
    @Autowired private DataSource dataSource;
    @Autowired private LegacyService service;
    @MockBean private LegacyBiliClient bili;
    private final ObjectMapper json = new ObjectMapper();
    private JsonNode fixture;

    @Before public void setup() throws Exception {
        fixture = json.readTree(getClass().getResourceAsStream("/legacy/fixture.json"));
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DROP ALL OBJECTS");
        jdbc.execute("CREATE TABLE tdd_update_log (added BIGINT, type INT, content VARCHAR(2000))");
        Set<String> strings = new HashSet<>(Arrays.asList("sex", "name", "face", "title", "pic",
                "typename", "arctype", "singer", "status", "newvids", "millvids", "comment"));
        Iterator<Map.Entry<String, JsonNode>> tables = fixture.get("tables").fields();
        while (tables.hasNext()) {
            Map.Entry<String, JsonNode> entry = tables.next();
            List<String> columns = new ArrayList<>();
            entry.getValue().get(0).fieldNames().forEachRemaining(columns::add);
            String schema = columns.stream().map(k -> "`" + k + "` " +
                    (strings.contains(k) ? "VARCHAR(2000)" : "BIGINT")).collect(Collectors.joining(","));
            jdbc.execute("CREATE TABLE " + entry.getKey() + " (" + schema + ")");
            String names = columns.stream().map(k -> "`" + k + "`").collect(Collectors.joining(","));
            String slots = columns.stream().map(k -> "?").collect(Collectors.joining(","));
            for (JsonNode row : entry.getValue()) {
                jdbc.update("INSERT INTO " + entry.getKey() + " (" + names + ") VALUES (" + slots + ")",
                        columns.stream().map(k -> row.get(k).asText()).toArray());
            }
        }
        when(bili.view(anyString(), anyString())).thenAnswer(call -> {
            String key = call.getArgument(0) + "=" + call.getArgument(1);
            JsonNode value = fixture.get("upstream").get(key);
            if (value == null) value = fixture.get("upstream").get("default");
            if (value.isBoolean()) throw new IOException("simulated upstream failure");
            return value.asText();
        });
    }

    private Reply request(String method, String path, String query, String origin) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:" + port + path +
                (query.isEmpty() ? "" : "?" + query.replace("[", "%5B").replace("]", "%5D"))).openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(10000);
        if (origin != null) connection.setRequestProperty("Origin", origin);
        int status = connection.getResponseCode();
        InputStream input = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (input != null) {
            try (InputStream in = input) {
                byte[] buffer = new byte[4096];
                int n;
                while ((n = in.read(buffer)) != -1) bytes.write(buffer, 0, n);
            }
        }
        Reply reply = new Reply(status, new String(bytes.toByteArray(), StandardCharsets.UTF_8),
                connection.getHeaderField("Access-Control-Allow-Origin"),
                connection.getHeaderField("Access-Control-Allow-Credentials"),
                connection.getHeaderField("Content-Type"));
        connection.disconnect();
        return reply;
    }

    @Test public void comparesAllPhpFixturesThroughFullHttpStack() throws Exception {
        List<String> failures = new ArrayList<>();
        for (JsonNode c : fixture.get("cases")) {
            String label = c.get("route").asText() + "?" + c.get("query").asText();
            Reply actual = request("GET", "/legacy/" + c.get("route").asText(),
                    c.get("query").asText(), "https://unlisted.example");
            String expected = c.get("expected").asText();
            boolean equal;
            if (expected.startsWith("{") || expected.startsWith("[")) {
                equal = json.readTree(expected).equals(json.readTree(actual.body));
            } else equal = expected.equals(actual.body);
            if (!equal || actual.status != 200) failures.add(label + "\nexpected=" + expected + "\nactual=" + actual.body);
            assertEquals(label, "*", actual.origin);
            assertNull(label, actual.credentials);
            assertEquals(label, "text/html;charset=UTF-8", actual.contentType);
        }
        assertTrue(String.join("\n", failures), failures.isEmpty());
    }

    @Test public void optionsAndPostUseQueryParametersAndWildcardCors() throws Exception {
        for (String method : Arrays.asList("OPTIONS", "POST")) {
            Reply actual = request(method, "/legacy/member", "mid=0", "http://localhost:8080");
            assertEquals(200, actual.status);
            assertEquals(0, json.readTree(actual.body).path("meta").path("count").asInt());
            assertEquals("*", actual.origin);
            assertNull(actual.credentials);
        }
    }

    @Test public void existingRoutesKeepTheirCorsAndErrorBehavior() throws Exception {
        Reply existing = request("GET", "/updatelog", "", "https://tdd.bunnyxt.com");
        assertEquals(200, existing.status);
        assertEquals("https://tdd.bunnyxt.com", existing.origin);
        assertEquals("true", existing.credentials);
        Reply rejected = request("GET", "/updatelog", "", "https://unlisted.example");
        assertEquals(403, rejected.status);
        Reply missing = request("GET", "/legacy/not-an-endpoint", "", null);
        assertEquals(404, missing.status);
    }

    @Test public void historyIsNotSilentlyTruncatedAndSqlValuesAreBound() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.update("INSERT INTO tdd_sprint_video_record (id,added,aid,view) " +
                "SELECT 1000+X, 300000+X, 100, X FROM SYSTEM_RANGE(1, 12000)");
        long started = System.nanoTime();
        Reply reply = request("GET", "/legacy/sprint_video_record", "aid=100", null);
        JsonNode response = json.readTree(reply.body);
        assertEquals(12004, response.path("meta").path("count").asInt());
        assertEquals(12004, response.path("data").size());
        assertEquals(1L, response.path("data").get(0).path("id").asLong());
        assertEquals(13000L, response.path("data").get(12003).path("id").asLong());
        System.out.println("history_fixture rows=12004 duration_ms=" + (System.nanoTime()-started)/1000000);
        assertEquals(0, service.videos(new LegacyParameters("status=%22%20OR%201%3D1%20--")).data.size());
    }

    @Test public void php70IntegerBoundaries() {
        assertEquals(1L, LegacyParameters.integer("1e3"));
        assertEquals(12L, LegacyParameters.integer("  +12.9"));
        assertEquals(Long.MAX_VALUE, LegacyParameters.integer("99999999999999999999999"));
        assertEquals(Long.MIN_VALUE, LegacyParameters.integer("-99999999999999999999999"));
        assertEquals(0L, LegacyParameters.integer("no number"));
    }

    @Test public void failedQueryRetainsPdoSilentModeWrapper() throws Exception {
        new JdbcTemplate(dataSource).execute("DROP TABLE tdd_sprint_video_record");
        Reply reply = request("GET", "/legacy/sprint_video_record", "aid=100", null);
        assertEquals(200, reply.status);
        assertEquals(0, json.readTree(reply.body).path("meta").path("count").asInt());
        assertEquals(0, json.readTree(reply.body).path("data").size());
    }

    private static final class Reply {
        final int status;
        final String body, origin, credentials, contentType;
        Reply(int status, String body, String origin, String credentials, String contentType) {
            this.status=status; this.body=body; this.origin=origin;
            this.credentials=credentials; this.contentType=contentType;
        }
    }
}
