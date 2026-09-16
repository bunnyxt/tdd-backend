package com.bunnyxt.tdd.util;

import java.util.Map;

public class TddRequestBodyUtil {

    // The user endpoints used to take a fastjson JSONObject and call its getString.
    // No FastJsonHttpMessageConverter was ever registered, so Jackson bound those
    // bodies all along and only the lookup came from fastjson. This keeps that
    // lookup's behaviour: null stays null, anything else goes through toString(),
    // so numbers, booleans, nested objects and arrays render as they did before.
    public static String getString(Map<String, Object> body, String key) {
        Object value = body.get(key);
        return value == null ? null : value.toString();
    }
}
