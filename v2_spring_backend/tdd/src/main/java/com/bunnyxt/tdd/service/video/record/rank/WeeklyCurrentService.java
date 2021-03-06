package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrent;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentEx;

import java.util.List;

public interface WeeklyCurrentService {

    List<WeeklyCurrentEx> queryWeeklyCurrentExs(String order_rule, Integer pn, Integer ps);

    Integer queryWeeklyCurrentExsCount();

    WeeklyCurrent queryWeeklyCurrentByBvid(String bvid);
}
