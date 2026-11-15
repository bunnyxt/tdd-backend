package com.bunnyxt.tdd.legacy;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Component
public class LegacyBiliClient {
    private final String viewUrl;

    public LegacyBiliClient(@Value("${tdd.bilibili.view-url:https://api.bilibili.com/x/web-interface/view}") String viewUrl) {
        this.viewUrl = viewUrl;
    }

    // The destination is deployment configuration; request input is never a URL.
    public String view(String key, String value) throws IOException {
        URL url = new URL(viewUrl + "?" + key + "=" +
                URLEncoder.encode(value, "UTF-8"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(60000);
        connection.setInstanceFollowRedirects(false);
        try {
            if (connection.getResponseCode() >= 400) return "";
            try (InputStream in = connection.getInputStream();
                 ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) bytes.write(buffer, 0, read);
                return new String(bytes.toByteArray(), StandardCharsets.UTF_8);
            }
        } finally { connection.disconnect(); }
    }
}
