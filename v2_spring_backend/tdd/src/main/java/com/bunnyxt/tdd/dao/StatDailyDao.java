package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.StatDaily;

import java.util.List;

public interface StatDailyDao {

    List<StatDaily> queryStatDailys(Integer start_ts, Integer end_ts, Integer offset, Integer ps);

    Integer queryStatDailysCount(Integer start_ts, Integer end_ts);

}
