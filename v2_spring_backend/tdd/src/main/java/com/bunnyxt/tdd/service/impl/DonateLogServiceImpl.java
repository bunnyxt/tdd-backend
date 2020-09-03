package com.bunnyxt.tdd.service.impl;

import com.bunnyxt.tdd.dao.DonateLogDao;
import com.bunnyxt.tdd.model.DonateLog;
import com.bunnyxt.tdd.service.DonateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonateLogServiceImpl implements DonateLogService {

    @Autowired
    DonateLogDao donateLogDao;

    @Override
    public List<DonateLog> queryDonateLogs() {
        return donateLogDao.queryDonateLogs();
    }
}
