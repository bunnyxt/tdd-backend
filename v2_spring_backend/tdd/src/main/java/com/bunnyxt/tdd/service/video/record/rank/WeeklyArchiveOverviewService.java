package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveOverview;

import java.util.List;

public interface WeeklyArchiveOverviewService {

    List<WeeklyArchiveOverview> queryWeeklyArchiveOverviews();

    WeeklyArchiveOverview queryWeeklyArchiveOverviewById(Long id);
}
