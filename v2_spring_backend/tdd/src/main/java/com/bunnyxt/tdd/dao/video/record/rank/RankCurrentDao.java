package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankCurrent;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrentEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankCurrentDao {

    List<RankCurrentEx> queryRankCurrentExs(String rank_name, String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryRankCurrentExsCount(@Param(value="rank_name") String rank_name);

    RankCurrent queryRankCurrentByBvid(String rank_name, String bvid);
}
