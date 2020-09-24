package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchive;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveEx;

import java.util.List;

public interface WeeklyArchiveService {

    List<WeeklyArchiveEx> queryWeeklyArchiveExsByArchId(Long arch_id, String order_rule, Integer pn, Integer ps);

    Integer queryWeeklyArchiveExsCountByArchId(Long arch_id);

    List<WeeklyArchive> queryWeeklyArchivesByBvid(String bvid);

    Integer queryWeeklyArchivesCountByBvid(String bvid);
}
