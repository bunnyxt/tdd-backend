package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoExServiceImpl implements VideoExService {

    @Autowired
    private VideoExDao videoExDao;

    @Override
    public VideoEx queryVideoByAid(int aid) {
        return videoExDao.queryVideoByAid(aid);
    }

    @Override
    public List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up,
                                     String order_by, int desc, int pn, int ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        int offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        List<String> allowedOrderBy = new ArrayList<>();
        allowedOrderBy.add("pubdate");
        allowedOrderBy.add("view");
        allowedOrderBy.add("danmaku");
        allowedOrderBy.add("reply");
        allowedOrderBy.add("favorite");
        allowedOrderBy.add("coin");
        allowedOrderBy.add("share");
        allowedOrderBy.add("like");
        if (!allowedOrderBy.contains(order_by)) {
            order_by = "pubdate";
        }
        if (order_by.equals("like")) {
            order_by = "r.like"; // cannot be like since like is a possible keyword there
        }

        // modify desc
        if (desc != 0 && desc != 1) {
            desc = 1;
        }

        return videoExDao.queryVideos(vc, start_ts, end_ts, title, up, order_by, desc, offset, ps);
    }

    @Override
    public int queryVideosCount(int vc, int start_ts, int end_ts, String title, String up) {
        return videoExDao.queryVideosCount(vc, start_ts, end_ts, title, up);
    }
}
