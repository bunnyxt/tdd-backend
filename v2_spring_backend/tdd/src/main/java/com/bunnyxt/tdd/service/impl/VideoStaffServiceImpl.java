package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoStaffDao;
import com.bunnyxt.tdd.model.VideoStaff;
import com.bunnyxt.tdd.service.VideoStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoStaffServiceImpl implements VideoStaffService {

    @Autowired
    private VideoStaffDao videoStaffDao;

    @Override
    public List<VideoStaff> queryVideoStaffsByAid(Long aid) {
        return videoStaffDao.queryVideoStaffsByAid(aid);
    }
}
