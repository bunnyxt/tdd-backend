package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.UpdateLog;

import java.util.List;

public interface UpdateLogService {

    List<UpdateLog> queryUpdateLogs(Integer last_count);

}
