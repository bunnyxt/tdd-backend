package com.bunnyxt.tdd.util;

import org.springframework.util.DigestUtils;

import java.util.Random;

public class TddCodeKeyGenerator {

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
        String salt = "<your-salt-string>";
        return DigestUtils.md5DigestAsHex((added + ":" + code + ":" + salt).getBytes());
    }
}
