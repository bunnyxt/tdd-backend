package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberLog;

import java.util.List;

public interface MemberLogService {

    List<MemberLog> queryMemberLogs(Long mid, Integer start_ts, Integer end_ts,
                                    String attr, String oldval, String newval,
                                    Integer pn, Integer ps);

    Integer queryMemberLogsCount(Long mid, Integer start_ts, Integer end_ts,
                                 String attr, String oldval, String newval);
}
