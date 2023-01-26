package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoAidDao;
import com.bunnyxt.tdd.service.VideoAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoAidServiceImpl implements VideoAidService {

    @Autowired
    VideoAidDao videoAidDao;

    @Override
    public Long queryVideoAidMaxId() {
        return videoAidDao.queryVideoAidMaxId();
    }

    @Override
    public Integer queryVideoAidById(Long id) {
        return videoAidDao.queryVideoAidById(id);
    }

    @Override
    public List<Integer> queryVideoAidsByIds(List<Long> ids) {
        return videoAidDao.queryVideoAidsByIds(ids);
    }
}
