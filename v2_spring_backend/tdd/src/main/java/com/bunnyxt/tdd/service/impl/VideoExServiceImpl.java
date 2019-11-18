package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoExServiceImpl implements VideoExService {

    @Autowired
    private VideoExDao videoExDao;

    private int modifyPs(int ps) {
        // modify ps to [0, 20]
        if (ps > 20) {
            ps = 20;
        } else if (ps < 0) {
            ps = 0;
        }
        return ps;
    }

    private int modifyPn(int pn) {
        // modify pn
        if (pn <= 0) {
            pn = 1;
        }
        return pn;
    }

    private int calcOffset(int pn, int ps) {
        // calc offset
        return ps * (pn - 1);
    }

    @Override
    public VideoEx queryVideoByAid(int aid) {
        return videoExDao.queryVideoByAid(aid);
    }

    @Override
    public List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up, int pn, int ps) {
        ps = modifyPs(ps);
        pn = modifyPn(pn);
        int offset = calcOffset(pn, ps);
        return videoExDao.queryVideos(vc, start_ts, end_ts, title, up, offset, ps);
    }
}
