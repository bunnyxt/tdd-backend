package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveOverview;

import java.util.List;

public interface WeeklyArchiveOverviewDao {

    List<WeeklyArchiveOverview> queryWeeklyArchiveOverviews();

    WeeklyArchiveOverview queryWeeklyArchiveOverviewById(Long id);
}
