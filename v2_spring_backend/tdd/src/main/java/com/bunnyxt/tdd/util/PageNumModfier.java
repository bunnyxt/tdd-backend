package com.bunnyxt.tdd.util;

public class PageNumModfier {

    public static Integer modifyPs(Integer ps, Integer limit) {
        // modify ps to [0, limit]
        if (ps > limit) {
            ps = limit;
        } else if (ps < 0) {
            ps = 0;
        }
        return ps;
    }

    public static Integer modifyPn(Integer pn) {
        // modify pn
        if (pn <= 0) {
            pn = 1;
        }
        return pn;
    }

    public static Integer calcOffset(Integer ps, Integer pn) {
        // calc offset
        return ps * (pn - 1);
    }

}
