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

    @Autowired
    private VideoStaffExDao videoStaffExDao;

    @Override
    public VideoEx queryVideoByAid(int aid) {
        VideoEx videoEx = videoExDao.queryVideoByAid(aid);

        // set staff
        if(videoEx.getHasstaff() == 1) {
            videoEx.setStaff(videoStaffExDao.queryVideoStaffsByAid(aid));
        }

        return videoEx;
    }

    @Override
    public List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up,
                                     String order_by, int desc, int pn, int ps) {
        // pn, ps -> offset, ps
        ps = PageNumModfier.modifyPs(ps, 20);
        pn = PageNumModfier.modifyPn(pn);
        int offset = PageNumModfier.calcOffset(ps, pn);

        // modify order_by
        if (order_by.equals("like")) {
            order_by = "r.like"; // cannot be like since like is a possible keyword there
        }

        List<VideoEx> videoExList = videoExDao.queryVideos(vc, start_ts, end_ts, title, up, order_by, desc, offset, ps);

        // set staff
        for (VideoEx videoEx : videoExList) {
            if (videoEx.getHasstaff() == 1) {
                int aid = videoEx.getAid();
                videoEx.setStaff(videoStaffExDao.queryVideoStaffsByAid(aid));
            }
        }

        return videoExList;
    }

    @Override
    public int queryVideosCount(int vc, int start_ts, int end_ts, String title, String up) {
        return videoExDao.queryVideosCount(vc, start_ts, end_ts, title, up);
    }
}
