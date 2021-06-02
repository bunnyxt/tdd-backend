package com.bunnyxt.tdd.util;

import com.bunnyxt.tdd.error.InvalidRequestParameterException;

import java.util.ArrayList;
import java.util.List;

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

    public static void bvid(String bvid) {
        // bvid should be 10 size length
        if (bvid.length() != 10) {
            throw new InvalidRequestParameterException("bvid", bvid, "bvid should be 10 size length");
        }
        // only support 1??4?1?7?? format
        if (!(bvid.charAt(0) == '1' && bvid.charAt(3) == '4' && bvid.charAt(5) == '1' && bvid.charAt(7) == '7')) {
            throw new InvalidRequestParameterException("bvid", bvid, "only support 1??4?1?7?? format");
        }
        // character should in table
        String table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
        for (int i = 0; i < 10; i++) {
            if (table.indexOf(bvid.charAt(i)) == -1) {
                // invalid character
                throw new InvalidRequestParameterException("bvid", bvid, "invalid character " + bvid.charAt(i) + "in bvid found");
            }
        }
    }

    public static void arch_id(Long id) {
        // default: 0, no limit
        _zero_plus(id, "arch_id");
    }

    public static void rank_name(String rank_name) {
        // only these rank name are allowed
        // WARNING must have corresponding table tdd_video_record_rank_${rank_name}_xxx
        List<String> allowedRankName = new ArrayList<String>(){{
            add("weekly");
            add("monthly");
        }};
        if (!allowedRankName.contains(rank_name)) {
            throw new InvalidRequestParameterException("rank_name", rank_name,
                    "only support rank name " + allowedRankName.toString());
        }
    }
}
