package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoRecordDao;
import com.bunnyxt.tdd.model.VideoRecord;
import com.bunnyxt.tdd.service.VideoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRecordServiceImpl implements VideoRecordService {

    @Autowired
    private VideoRecordDao videoRecordDao;

    @Override
    public List<VideoRecord> queryVideoRecordsByAid(int aid) {
        return videoRecordDao.queryVideoRecordsByAid(aid);
    }

}
