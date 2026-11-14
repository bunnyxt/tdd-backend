package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoLog;

import java.util.List;

public interface VideoLogService {

    List<VideoLog> queryVideoLogs(Long aid, Long start_ts, Long end_ts,
                                  String attr, String oldval, String newval,
                                  Integer pn, Integer ps);

    Long queryVideoLogsCount(Long aid, Long start_ts, Long end_ts,
                                String attr, String oldval, String newval);
}
