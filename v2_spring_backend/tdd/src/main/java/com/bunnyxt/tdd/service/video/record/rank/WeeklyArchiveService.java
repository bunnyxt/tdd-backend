package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchive;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveEx;

import java.util.List;

public interface WeeklyArchiveService {

    List<WeeklyArchiveEx> queryWeeklyArchiveExsById(Long id, String order_rule, Integer pn, Integer ps);

    Integer queryWeeklyArchiveExsCountById(Long id);

    List<WeeklyArchive> queryWeeklyArchivesByBvid(String bvid);

    Integer queryWeeklyArchivesCountByBvid(String bvid);
}
