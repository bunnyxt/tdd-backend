package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyArchiveOverviewDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveOverview;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyArchiveOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyArchiveOverviewServiceImpl implements WeeklyArchiveOverviewService {

    @Autowired
    WeeklyArchiveOverviewDao weeklyArchiveOverviewDao;

    @Override
    public List<WeeklyArchiveOverview> queryWeeklyArchiveOverviews() {
        return weeklyArchiveOverviewDao.queryWeeklyArchiveOverviews();
    }

    @Override
    public WeeklyArchiveOverview queryWeeklyArchiveOverviewByArchId(Long arch_id) {
        return weeklyArchiveOverviewDao.queryWeeklyArchiveOverviewByArchId(arch_id);
    }
}
