package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrent;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyCurrentEx;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyServiceImpl implements WeeklyService {

    @Autowired
    WeeklyDao weeklyDao;

    @Override
    public List<WeeklyCurrentEx> queryWeeklyCurrentExs(String order_rule, Integer pn, Integer ps) {
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

        return weeklyDao.queryWeeklyCurrentExs(order_by, desc, offset, ps);
    }

    @Override
    public Integer queryWeeklyCurrentExsCount() {
        return weeklyDao.queryWeeklyCurrentExsCount();
    }

    @Override
    public WeeklyCurrent queryWeeklyCurrentByBvid(String bvid) {
        return weeklyDao.queryWeeklyCurrentByBvid(bvid);
    }
}
