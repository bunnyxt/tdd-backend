package com.bunnyxt.tdd.dao.video.record.rank;

import java.util.List;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchive;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveEx;

public interface WeeklyArchiveDao {

    List<WeeklyArchiveEx> queryWeeklyArchiveExsByArchId(Long arch_id, String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryWeeklyArchiveExsCountByArchId(Long arch_id);

    List<WeeklyArchive> queryWeeklyArchivesByBvid(String bvid);

    Integer queryWeeklyArchivesCountByBvid(String bvid);
}
