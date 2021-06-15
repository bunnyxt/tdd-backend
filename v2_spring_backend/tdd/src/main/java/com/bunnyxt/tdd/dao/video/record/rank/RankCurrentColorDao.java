package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankColor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankCurrentColorDao {

    List<RankColor> queryRankCurrentColors(@Param(value="rank_name") String rank_name);
}
