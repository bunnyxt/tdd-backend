package com.bunnyxt.tdd.util;

import java.util.Arrays;
import java.util.List;

public class TddAbidUtil {

    static long XOR_CODE = 23442827791579L;
    static long MASK_CODE = 2251799813685247L;
    static long MAX_AID = 1L << 51;

    static String ALPHABET = "FcwAPNKTMug3GV5Lj7EJnHpWsx4tb8haYeviqBz6rkCy12mUSDQX9RdoZf";

    static List<Integer> ENCODE_MAP = Arrays.asList(8, 7, 0, 5, 1, 3, 2, 4, 6);
    static List<Integer> DECODE_MAP = Arrays.asList(6, 4, 2, 3, 1, 5, 0, 7, 8);

    static int BASE = ALPHABET.length();
    static String PREFIX = "BV1";
    static int PREFIX_LEN = PREFIX.length();
    static int CODE_LEN = ENCODE_MAP.size();

    public static String a2b(long aid) {
        char[] bvid = new char[9];
        long tmp = (MAX_AID | aid) ^ XOR_CODE;
        for (int i = 0; i < CODE_LEN; i++) {
            bvid[ENCODE_MAP.get(i)] = ALPHABET.charAt((int) (tmp % BASE));
            tmp /= BASE;
        }
        return (PREFIX + new String(bvid)).substring(2);  // remove BV prefix
    }


    // no BV prefix provided
    public static long b2a(String bvid) {
        bvid = "BV" + bvid;
        if (!bvid.substring(0, 3).equals(PREFIX)) {
            throw new IllegalArgumentException("Invalid prefix in bvid: " + bvid);
        }

        long tmp = 0;
        bvid = bvid.substring(3);
        for (int i = 0; i < CODE_LEN; i++) {
            int idx = ALPHABET.indexOf(bvid.charAt(DECODE_MAP.get(i)));
            tmp = tmp * BASE + idx;
        }
        return (tmp & MASK_CODE) ^ XOR_CODE;
    }

    public static void main(String[] args) {
        long aid1 = 1152551018L;
        String bvid1 = a2b(aid1);
        System.out.println(bvid1); // Output: 1MZ421B7CB
        long decodedAid1 = b2a(bvid1);
        System.out.println(decodedAid1); // Output: 1152551018
    }
}
