package com.bunnyxt.tdd.dao.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankColor;

import java.util.List;

public interface RankArchiveColorDao {

    List<RankColor> queryRankArchiveColorsByArchId(String rank_name, Long arch_id);
}
