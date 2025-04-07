package com.bunnyxt.tdd.util;

import java.math.BigInteger;

// ref: https://github.com/SocialSisterYi/bilibili-API-collect/blob/master/docs/misc/bvid_desc.md#java

public class TddAbidUtil {

    private static final BigInteger XOR_CODE = BigInteger.valueOf(23442827791579L);
    private static final BigInteger MASK_CODE = BigInteger.valueOf(2251799813685247L);
    private static final BigInteger MAX_AID = BigInteger.ONE.shiftLeft(51);
    private static final int BASE = 58;

    private static final String DATA = "FcwAPNKTMug3GV5Lj7EJnHpWsx4tb8haYeviqBz6rkCy12mUSDQX9RdoZf";

    public static String a2b(Long aid) {
        BigInteger aidBigInteger = BigInteger.valueOf(aid);
        char[] bytes = {'B', 'V', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
        int bvIndex = bytes.length - 1;
        BigInteger tmp = MAX_AID.or(aidBigInteger).xor(XOR_CODE);
        while (tmp.compareTo(BigInteger.ZERO) > 0) {
            bytes[bvIndex] = DATA.charAt(tmp.mod(BigInteger.valueOf(BASE)).intValue());
            tmp = tmp.divide(BigInteger.valueOf(BASE));
            bvIndex--;
        }
        swap(bytes, 3, 9);
        swap(bytes, 4, 7);
        return new String(bytes).substring(2); // remove BV prefix
    }

    public static Long b2a(String bvid) {
        char[] bvidArr = ("BV" + bvid).toCharArray(); // add BV prefix
        swap(bvidArr, 3, 9);
        swap(bvidArr, 4, 7);
        String adjustedBvid = new String(bvidArr, 3, bvidArr.length - 3);
        BigInteger tmp = BigInteger.ZERO;
        for (char c : adjustedBvid.toCharArray()) {
            tmp = tmp.multiply(BigInteger.valueOf(BASE)).add(BigInteger.valueOf(DATA.indexOf(c)));
        }
        BigInteger xor = tmp.and(MASK_CODE).xor(XOR_CODE);
        return xor.longValue();
    }

    private static void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
