package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.RankArchiveColorDao;
import com.bunnyxt.tdd.model.video.record.rank.RankColor;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankArchiveColorServiceImpl implements RankArchiveColorService {

    @Autowired
    RankArchiveColorDao rankArchiveColorDao;

    @Override
    public List<RankColor> queryRankArchiveColorByArchId(String rank_name, Long arch_id) {
        return rankArchiveColorDao.queryRankArchiveColorsByArchId(rank_name, arch_id);
    }
}
