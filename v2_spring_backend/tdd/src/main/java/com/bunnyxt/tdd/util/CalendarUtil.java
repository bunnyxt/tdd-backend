package com.bunnyxt.tdd.util;

import java.util.Calendar;

public class CalendarUtil {

    public static Integer getNowTs() {
        Calendar calendar = Calendar.getInstance();
        return Integer.parseInt(String.valueOf(calendar.getTimeInMillis() / 1000));
    }

    public static Integer getTodayStartTs() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return Integer.parseInt(String.valueOf(calendar.getTimeInMillis() / 1000));
    }

    public static Integer getTodayEndTs() {
        return getTodayStartTs() + 24 * 60 * 60 - 1;
    }

}
