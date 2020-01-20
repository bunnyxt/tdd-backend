package com.bunnyxt.tdd.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjUtil {

    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();

        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }

        return map;
    }
}
