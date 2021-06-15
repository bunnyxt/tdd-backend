package com.bunnyxt.tdd.service.video.record.rank;

import com.bunnyxt.tdd.model.video.record.rank.RankColor;

import java.util.List;

public interface RankCurrentColorService {

    List<RankColor> queryRankCurrentColors(String rank_name);

    List<RankColor> queryRankArchiveColorByArchId(String rank_name, Long arch_id);
}
