package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrent;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentEx;
import java.util.List;

public interface WeeklyDao {

    List<WeeklyCurrentEx> queryWeeklyCurrentExs(String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryWeeklyCurrentExsCount();

    WeeklyCurrent queryWeeklyCurrentByBvid(String bvid);
}
