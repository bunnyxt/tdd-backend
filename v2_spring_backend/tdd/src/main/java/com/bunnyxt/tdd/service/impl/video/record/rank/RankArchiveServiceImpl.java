package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.RankArchiveDao;
import com.bunnyxt.tdd.model.video.record.rank.RankArchive;
import com.bunnyxt.tdd.model.video.record.rank.RankArchiveEx;
import com.bunnyxt.tdd.service.video.record.rank.RankArchiveService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankArchiveServiceImpl implements RankArchiveService {

    @Autowired
    RankArchiveDao rankArchiveDao;

    @Override
    public List<RankArchiveEx> queryRankArchiveExsByArchId(String rank_name, Long arch_id, String order_rule, Integer pn, Integer ps) {
        // order_rule -> order_by & desc
        String order_by;
        Integer desc;
        switch (order_rule) {
            case "incr_view":
            case "incr_danmaku":
            case "incr_reply":
            case "incr_favorite":
            case "incr_coin":
            case "incr_share":
            case "incr_like":
                order_by = order_rule;
                desc = 1;
                break;
            case "rank":
            default:
                order_by = "rank";
                desc = 0;
                break;
        }

        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 30);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        return rankArchiveDao.queryRankArchiveExsByArchId(rank_name, arch_id, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryRankArchiveExsCountByArchId(String rank_name, Long arch_id) {
        return rankArchiveDao.queryRankArchiveExsCountByArchId(rank_name, arch_id);
    }

    @Override
    public List<RankArchive> queryRankArchivesByBvid(String rank_name, String bvid) {
        return rankArchiveDao.queryRankArchivesByBvid(rank_name, bvid);
    }

    @Override
    public Integer queryRankArchivesCountByBvid(String rank_name, String bvid) {
        return rankArchiveDao.queryRankArchivesCountByBvid(rank_name, bvid);
    }
}
