package com.bunnyxt.tdd.util;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;

public class TddParamCheckUtil {

    public static void desc(Integer desc) {
        // default: 0 or 1, show desc order or not
        if (desc != 0 && desc != 1) {
            throw new InvalidRequestParameterException("desc", desc, "desc should be 0 or 1");
        }
    }

    public static void pn(Integer pn) {
        // default: 1, show first page
        if (pn <= 0) {
            throw new InvalidRequestParameterException("pn", pn, "pn should be greater than 0");
        }
    }

    public static void ps(Integer ps, Integer maxPs) {
        // default: maxPs, show as many as possible
        if (ps <= 0 || ps > maxPs) {
            throw new InvalidRequestParameterException("ps", ps, "ps should between 1 and " + maxPs);
        }
    }
}
