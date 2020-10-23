package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VideoBvidTitleDao;
import com.bunnyxt.tdd.model.VideoBvidTitle;
import com.bunnyxt.tdd.service.VideoBvidTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoBvidTitleServiceImpl implements VideoBvidTitleService {

    @Autowired
    VideoBvidTitleDao videoBvidTitleDao;

    @Override
    public List<VideoBvidTitle> queryVideoBvidTitle(String bvid) {
        return videoBvidTitleDao.queryVideoBvidTitle(bvid);
    }
}
