package com.bunnyxt.tdd.legacy;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Query-string only, last scalar wins, matching PHP 7.0's integer prefix conversion. */
public final class LegacyParameters {
    private static final Pattern INTEGER = Pattern.compile("^[\\s]*([+-]?[0-9]+)");
    private final Map<String, Object> values = new LinkedHashMap<>();

    public LegacyParameters(String query) {
        if (query == null) return;
        for (String part : query.split("&")) {
            String[] pair = part.split("=", 2);
            String key = decode(pair[0]);
            String value = pair.length == 2 ? decode(pair[1]) : "";
            int bracket = key.indexOf('[');
            if (bracket >= 0) values.put(key.substring(0, bracket), Boolean.TRUE);
            else values.put(key, value);
        }
    }

    private static String decode(String value) {
        try { return URLDecoder.decode(value, StandardCharsets.UTF_8.name()); }
        catch (java.io.UnsupportedEncodingException impossible) { throw new AssertionError(impossible); }
    }

    public String string(String key, String fallback) {
        Object value = values.get(key);
        return value == null ? fallback : value == Boolean.TRUE ? "Array" : value.toString();
    }

    public long number(String key, long fallback) {
        Object value = values.get(key);
        return value == null ? fallback : value == Boolean.TRUE ? 1 : integer(value.toString());
    }

    public static long integer(String value) {
        if (value == null) return 0;
        Matcher matcher = INTEGER.matcher(value);
        if (!matcher.find()) return 0;
        BigInteger parsed = new BigInteger(matcher.group(1));
        if (parsed.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) return Long.MAX_VALUE;
        if (parsed.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) return Long.MIN_VALUE;
        return parsed.longValue();
    }
}
