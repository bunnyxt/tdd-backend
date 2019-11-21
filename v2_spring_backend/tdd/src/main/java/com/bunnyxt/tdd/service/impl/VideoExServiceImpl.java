package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoExDao;
import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.service.VideoExService;
import com.bunnyxt.tdd.util.PageNumModfier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up, int pn, int ps) {
        PageNumModfier.modifyPs(ps, 20);
        PageNumModfier.modifyPn(pn);
        int offset = PageNumModfier.calcOffset(ps, pn);
        return videoExDao.queryVideos(vc, start_ts, end_ts, title, up, offset, ps);
    }
}
