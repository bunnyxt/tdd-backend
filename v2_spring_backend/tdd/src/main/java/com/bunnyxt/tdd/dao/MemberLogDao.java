package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberLog;

import java.util.List;

public interface MemberLogDao {

    List<MemberLog> queryMemberLogs(Long mid, Long start_ts, Long end_ts,
                                    String attr, String oldval, String newval,
                                    Long offset, Integer ps);

    Long queryMemberLogsCount(Long mid, Long start_ts, Long end_ts,
                                 String attr, String oldval, String newval);
}
