package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankArchiveOverview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankArchiveOverviewDao {

    List<RankArchiveOverview> queryRankArchiveOverviews(@Param(value="rank_name") String rank_name);

    RankArchiveOverview queryRankArchiveOverviewByArchId(String rank_name, Long arch_id);
}
