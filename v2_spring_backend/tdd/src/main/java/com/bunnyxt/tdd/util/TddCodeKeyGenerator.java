package com.bunnyxt.tdd.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import java.util.Random;

public class TddCodeKeyGenerator {

    @Value("${tdd.auth.salt}")
    private static String SALT;

    public static String generateCode() {
        Random rand = new Random(System.currentTimeMillis());
        Integer randCode = rand.nextInt(1000000);
        String code = String.valueOf(randCode);
        int prefixLength = 6 - code.length();
        for (int i = 0; i < prefixLength; i++) {
            code = "0" + code;
        }
        return code;
    }

    public static String generateKeyViaCode(String code) {
        Integer added = CalendarUtil.getNowTs();
        return DigestUtils.md5DigestAsHex((added + ":" + code + ":" + SALT).getBytes());
    }
}
