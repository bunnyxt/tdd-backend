package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoLog;

import java.util.List;

public interface VideoLogService {

    List<VideoLog> queryVideoLogs(Long aid, Integer start_ts, Integer end_ts,
                                  String attr, String oldval, String newval,
                                  Integer pn, Integer ps);

    Integer queryVideoLogsCount(Long aid, Integer start_ts, Integer end_ts,
                                String attr, String oldval, String newval);
}
