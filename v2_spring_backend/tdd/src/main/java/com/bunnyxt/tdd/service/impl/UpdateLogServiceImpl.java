package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.UpdateLogDao;
import com.bunnyxt.tdd.model.UpdateLog;
import com.bunnyxt.tdd.service.UpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateLogServiceImpl implements UpdateLogService {

    @Autowired
    UpdateLogDao updateLogDao;

    @Override
    public List<UpdateLog> queryUpdateLogs(Integer last_count) {
        return updateLogDao.queryUpdateLogs(last_count);
    }
}
