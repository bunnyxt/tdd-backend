package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankArchiveOverview;

import java.util.List;

public interface RankArchiveOverviewService {

    List<RankArchiveOverview> queryRankArchiveOverviews(String rank_name);

    RankArchiveOverview queryRankArchiveOverviewByArchId(String rank_name, Long arch_id);
}
