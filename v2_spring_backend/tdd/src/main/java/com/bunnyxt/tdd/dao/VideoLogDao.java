package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoLog;

import java.util.List;

public interface VideoLogDao {

    List<VideoLog> queryVideoLogs(Long aid, Long start_ts, Long end_ts,
                                  String attr, String oldval, String newval,
                                  Long offset, Integer ps);

    Long queryVideoLogsCount(Long aid, Long start_ts, Long end_ts,
                                String attr, String oldval, String newval);

}
