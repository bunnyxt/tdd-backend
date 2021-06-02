package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.RankCurrentDao;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrent;
import com.bunnyxt.tdd.model.video.record.rank.RankCurrentEx;
import com.bunnyxt.tdd.service.video.record.rank.RankCurrentService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankCurrentServiceImpl implements RankCurrentService {

    @Autowired
    RankCurrentDao rankCurrentDao;

    @Override
    public List<RankCurrentEx> queryRankCurrentExs(String rank_name, String order_rule, Integer pn, Integer ps) {
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

        return rankCurrentDao.queryRankCurrentExs(rank_name, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryRankCurrentExsCount(String rank_name) {
        return rankCurrentDao.queryRankCurrentExsCount(rank_name);
    }

    @Override
    public RankCurrent queryRankCurrentByBvid(String rank_name, String bvid) {
        return rankCurrentDao.queryRankCurrentByBvid(rank_name, bvid);
    }
}
