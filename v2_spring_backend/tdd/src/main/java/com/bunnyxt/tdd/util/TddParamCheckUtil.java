package com.bunnyxt.tdd.util;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;

public class TddParamCheckUtil {

    private static void _zero_plus(Integer value, String name) {
        if (value < 0) {
            throw new InvalidRequestParameterException(name, value, name + " should be greater than 0");
        }
    }

    private static void _zero_plus(Long value, String name) {
        if (value < 0) {
            throw new InvalidRequestParameterException(name, value, name + " should be greater than 0");
        }
    }

    public static void userid(Long userid){
        // default: 0, no limit
        _zero_plus(userid, "userid");
    }

    public static void start_ts(Integer start_ts){
        // default: 0, no limit
        _zero_plus(start_ts, "start_ts");
    }

    public static void end_ts(Integer end_ts){
        // default: 0, no limit
        _zero_plus(end_ts, "end_ts");
    }

    public static void last_count(Integer last_count, Integer maxLast_count) {
        // default: 0, no limit, use pn & ps
        if (last_count < 0 || last_count > maxLast_count) {
            throw new InvalidRequestParameterException("last_count", last_count, "last_count should between 0 and " + maxLast_count);
        }
    }

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

    private static void _not_null(String value, String name) {
        if (value == null) {
            throw new InvalidRequestParameterException(name, null, name + " should not be null");
        }
    }

    public static void bindkey(String bindkey) {
        _not_null(bindkey, "bindkey");
    }

    public static void code(String code) {
        _not_null(code, "code");
    }
}
