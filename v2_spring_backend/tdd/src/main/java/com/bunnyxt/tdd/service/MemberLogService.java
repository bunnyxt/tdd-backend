package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberLog;

import java.util.List;

public interface MemberLogService {

    List<MemberLog> queryMemberLogs(Long mid, Long start_ts, Long end_ts,
                                    String attr, String oldval, String newval,
                                    Integer pn, Integer ps);

    Long queryMemberLogsCount(Long mid, Long start_ts, Long end_ts,
                                 String attr, String oldval, String newval);
}
