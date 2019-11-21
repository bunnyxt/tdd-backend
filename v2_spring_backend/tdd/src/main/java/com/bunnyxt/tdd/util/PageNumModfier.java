package com.bunnyxt.tdd.util;

public class PageNumModfier {

    public static int modifyPs(int ps, int limit) {
        // modify ps to [0, limit]
        if (ps > limit) {
            ps = limit;
        } else if (ps < 0) {
            ps = 0;
        }
        return ps;
    }

    public static int modifyPn(int pn) {
        // modify pn
        if (pn <= 0) {
            pn = 1;
        }
        return pn;
    }

    public static int calcOffset(int ps, int pn) {
        // calc offset
        return ps * (pn - 1);
    }

}
