package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoStaffExDao;
import com.bunnyxt.tdd.model.VideoStaffEx;
import com.bunnyxt.tdd.service.VideoStaffExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoStaffExServiceImpl implements VideoStaffExService {
    @Autowired
    private VideoStaffExDao videoStaffExDao;

    @Override
    public List<VideoStaffEx> queryVideoStaffsByAid(int aid) {
        return videoStaffExDao.queryVideoStaffsByAid(aid);
    }
}
