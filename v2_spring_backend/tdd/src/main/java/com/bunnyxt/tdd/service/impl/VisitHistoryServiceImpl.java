package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.VisitHistoryDao;
import com.bunnyxt.tdd.service.VisitHistoryService;
import com.bunnyxt.tdd.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitHistoryServiceImpl implements VisitHistoryService {

    @Autowired
    VisitHistoryDao visitHistoryDao;

    @Override
    public void addVisitHistoryVideo(Long userid, String bvid) {
        Integer added = CalendarUtil.getNowTs();
        bvid = bvid.substring(0, 10);  // length of bvid should be 10
        visitHistoryDao.addVisitHistoryVideo(added, userid, bvid);
    }
}
