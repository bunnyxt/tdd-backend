package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoLog;

import java.util.List;

public interface VideoLogDao {

    List<VideoLog> queryVideoLogs(Long aid, Integer start_ts, Integer end_ts,
                                  String attr, String oldval, String newval,
                                  Integer offset, Integer ps);

    Integer queryVideoLogsCount(Long aid, Integer start_ts, Integer end_ts,
                                String attr, String oldval, String newval);

}
