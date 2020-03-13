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
    public void addVisitVideoRecord(Integer aid, Long userid) {
        Calendar calendar = Calendar.getInstance();
        Integer added = Integer.parseInt(String.valueOf(calendar.getTimeInMillis() / 1000));
        taskDao.addVisitVideoRecord(added, aid, userid);
    }
}
