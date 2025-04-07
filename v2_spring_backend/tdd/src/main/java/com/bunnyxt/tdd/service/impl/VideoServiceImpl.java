package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.model.Video;
import com.bunnyxt.tdd.service.VideoService;
import com.bunnyxt.tdd.dao.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    @Override
    public void updateVideoByAid(Long aid, Video video) {
        videoDao.updateVideoByAid(aid, video);
    }

    @Override
    public Video queryVideoByAid(Long aid) {
        return videoDao.queryVideoByAid(aid);
    }
}
