package com.bunnyxt.tdd.service.impl.video.record.rank;

import com.bunnyxt.tdd.dao.video.record.rank.WeeklyArchiveDao;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchive;
import com.bunnyxt.tdd.model.video.record.rank.WeeklyArchiveEx;
import com.bunnyxt.tdd.service.video.record.rank.WeeklyArchiveService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyArchiveServiceImpl implements WeeklyArchiveService {

    @Autowired
    WeeklyArchiveDao weeklyArchiveDao;

    @Override
    public List<WeeklyArchiveEx> queryWeeklyArchiveExsById(Long id, String order_rule, Integer pn, Integer ps) {
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

        return weeklyArchiveDao.queryWeeklyArchiveExsById(id, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryWeeklyArchiveExsCountById(Long id) {
        return weeklyArchiveDao.queryWeeklyArchiveExsCountById(id);
    }

    @Override
    public List<WeeklyArchive> queryWeeklyArchivesByBvid(String bvid) {
        return weeklyArchiveDao.queryWeeklyArchivesByBvid(bvid);
    }

    @Override
    public Integer queryWeeklyArchivesCountByBvid(String bvid) {
        return weeklyArchiveDao.queryWeeklyArchivesCountByBvid(bvid);
    }
}
