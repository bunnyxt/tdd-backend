package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.RankArchiveOverviewDao;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveOverview;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankArchiveOverviewServiceImpl implements RankArchiveOverviewService {

    @Autowired
    RankArchiveOverviewDao rankArchiveOverviewDao;

    @Override
    public List<RankArchiveOverview> queryRankArchiveOverviews(String rank_name) {
        return rankArchiveOverviewDao.queryRankArchiveOverviews(rank_name);
    }

    @Override
    public RankArchiveOverview queryRankArchiveOverviewByArchId(String rank_name, Long arch_id) {
        return rankArchiveOverviewDao.queryRankArchiveOverviewByArchId(rank_name, arch_id);
    }
}
