package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankArchive;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveEx;

import java.util.List;

public interface RankArchiveService {

    List<RankArchiveEx> queryRankArchiveExsByArchId(String rank_name, Long arch_id, String order_rule, Integer pn, Integer ps);

    Integer queryRankArchiveExsCountByArchId(String rank_name, Long arch_id);

    List<RankArchive> queryRankArchivesByBvid(String rank_name, String bvid);

    Integer queryRankArchivesCountByBvid(String rank_name, String bvid);
}
