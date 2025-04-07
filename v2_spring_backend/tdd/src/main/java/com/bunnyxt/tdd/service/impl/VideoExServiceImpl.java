package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.dao.VideoStaffExDao;
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
    public VideoEx queryVideoByAid(Long aid) {
        return videoExDao.queryVideoByAid(aid);
    }

    @Override
    public List<VideoEx> queryVideos(Integer vc, Integer start_ts, Integer end_ts, Integer activity, Integer recent,
                                     String title, String up, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("like")) {
            order_by = "r.like"; // cannot be like since like is a possible keyword there
        }

        return videoExDao.queryVideos(vc, start_ts, end_ts, activity, recent, title, up, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryVideosCount(Integer vc, Integer start_ts, Integer end_ts, Integer activity, Integer recent, String title, String up) {
        return videoExDao.queryVideosCount(vc, start_ts, end_ts, activity, recent, title, up);
    }

    @Override
    public List<VideoEx> queryVideosByMid(Long mid, String order_by, Integer desc, Integer pn, Integer ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        Integer offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("like")) {
            order_by = "r.like"; // cannot be like since like is a possible keyword there
        }

        return videoExDao.queryVideosByMid(mid, order_by, desc, offset, ps);
    }

    @Override
    public Integer queryVideosByMidCount(Long mid) {
        return videoExDao.queryVideosByMidCount(mid);
    }
}
