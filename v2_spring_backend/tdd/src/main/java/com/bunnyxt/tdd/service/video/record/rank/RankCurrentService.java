package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankCurrent;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrentEx;

import java.util.List;

public interface RankCurrentService {

    List<RankCurrentEx> queryRankCurrentExs(String rank_name, String order_rule, Integer pn, Integer ps);

    Integer queryRankCurrentExsCount(String rank_name);

    RankCurrent queryRankCurrentByBvid(String rank_name, String bvid);
}
