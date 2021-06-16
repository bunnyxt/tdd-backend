package com.bunnyxt.tdd.dao.video.record.rank;

import java.util.List;

import com.bunnyxt.tdd.model.video.record.rank.RankArchive;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveEx;

public interface RankArchiveDao {

    List<RankArchiveEx> queryRankArchiveExsByArchId(String rank_name, Long arch_id, String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryRankArchiveExsCountByArchId(String rank_name, Long arch_id);

    List<RankArchive> queryRankArchivesByBvid(String rank_name, String bvid);

    Integer queryRankArchivesCountByBvid(String rank_name, String bvid);
}
