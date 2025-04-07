package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoAidTitleDao;
import com.bunnyxt.tdd.model.VideoAidTitle;
import com.bunnyxt.tdd.service.VideoAidTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoAidTitleServiceImpl implements VideoAidTitleService {

    @Autowired
    VideoAidTitleDao videoAidTitleDao;

    @Override
    public List<VideoAidTitle> queryVideoAidTitle(Long aid) {
        return videoAidTitleDao.queryVideoAidTitle(aid);
    }
}
