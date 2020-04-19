package com.bunnyxt.tdd.util;

import java.util.HashMap;
import java.util.Map;

public class TddAbidUtil {

    private static String table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    private static int[] s = {11, 10, 3, 8, 4, 6};
    private static Long xor = 177451812L;
    private static Long add = 8728348608L;
    private static Map<Character, Integer> tr;

    private static void initTr() {
        tr = new HashMap<>();
        for (int i = 0; i < 58; i++) {
            tr.put(table.charAt(i), i);
        }
    }

    public static String a2b(Integer aid) {
        Long x = Long.valueOf(aid);
        x = (x ^ xor) + add;
        char[] r = {'B', 'V', '1', ' ', ' ', '4', ' ', '1', ' ', '7', ' ', ' '};
        for (int i = 0; i < 6; i++) {
            r[s[i]] = table.charAt((int)(x / new Double(Math.pow(58, i)).longValue() % 58));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (char rr: r) {
            stringBuilder.append(rr);
        }
        return stringBuilder.toString().substring(2); // remove BV prefix
    }

    public static Integer b2a(String bvid) {
        if (tr == null) {
            initTr();
        }
        Long r = 0L;
        for (int i = 0; i < 6; i++) {
            r += tr.get(bvid.charAt(s[i] - 2)) * new Double(Math.pow(58, i)).longValue(); // no BV prefix provided
        }
        return new Long((r - add) ^ xor).intValue();
    }
}
