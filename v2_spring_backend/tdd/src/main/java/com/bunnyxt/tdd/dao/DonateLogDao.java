package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.DonateLog;

import java.util.List;

public interface DonateLogDao {

    List<DonateLog> queryDonateLogs();
}
