package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberLog;

import java.util.List;

public interface MemberLogDao {

    List<MemberLog> queryMemberLogs(Long mid, Integer start_ts, Integer end_ts,
                                    String attr, String oldval, String newval,
                                    Integer offset, Integer ps);

    Integer queryMemberLogsCount(Long mid, Integer start_ts, Integer end_ts,
                                 String attr, String oldval, String newval);
}
