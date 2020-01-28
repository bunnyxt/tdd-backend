package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.StatDaily;

import java.util.List;

public interface StatDailyService {

    List<StatDaily> queryStatDailys(Integer start_ts, Integer end_ts, Integer pn, Integer ps);

    Integer queryStatDailysCount(Integer start_ts, Integer end_ts);

}
