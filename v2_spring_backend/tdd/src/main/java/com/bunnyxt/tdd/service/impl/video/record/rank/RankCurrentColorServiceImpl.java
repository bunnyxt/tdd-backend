package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.RankCurrentColorDao;
import com.bunnyxt.tdd.model.video.record.rank.RankColor;
import com.bunnyxt.tdd.service.video.record.rank.RankCurrentColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankCurrentColorServiceImpl implements RankCurrentColorService {

    @Autowired
    RankCurrentColorDao rankCurrentColorDao;

    @Override
    public List<RankColor> queryRankCurrentColors(String rank_name) {
        return rankCurrentColorDao.queryRankCurrentColors(rank_name);
    }

    @Override
    public List<RankColor> queryRankArchiveColorByArchId(String rank_name, Long arch_id) {
        return rankCurrentColorDao.queryRankArchiveColorsByArchId(rank_name, arch_id);
    }
}
