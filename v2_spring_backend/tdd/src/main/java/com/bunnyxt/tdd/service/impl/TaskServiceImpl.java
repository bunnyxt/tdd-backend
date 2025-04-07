package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.TaskDao;
import com.bunnyxt.tdd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDao taskDao;

    @Override
    public void addVisitVideoRecord(Long aid, Long userid) {
        Integer added = (int)(System.currentTimeMillis() / 1000);
        taskDao.addVisitVideoRecord(added, aid, userid);
    }
}
