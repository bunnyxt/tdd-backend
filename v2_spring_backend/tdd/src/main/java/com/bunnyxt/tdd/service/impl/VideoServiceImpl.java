package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import com.bunnyxt.tdd.dao.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    private int modifyPs(int ps) {
        // modify ps to [0, 50]
        if (ps > 50) {
            ps = 50;
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
    public Video queryVideoByAid(int aid) {
        return videoDao.queryVideoByAid(aid);
    }

    @Override
    public List<Video> queryVideosByTid(int tid, int pn, int ps) {
        ps = modifyPs(ps);
        pn = modifyPn(pn);
        int offset = calcOffset(ps, pn);
        return videoDao.queryVideosByTid(tid, offset, ps);
    }

    @Override
    public List<Video> queryVideosVc(int pn, int ps) {
        ps = modifyPs(ps);
        pn = modifyPn(pn);
        int offset = calcOffset(ps, pn);
        return videoDao.queryVideosVc(offset, ps);
    }
}
