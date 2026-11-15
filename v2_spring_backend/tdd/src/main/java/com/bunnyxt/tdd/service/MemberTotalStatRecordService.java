package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberTotalStatRecord;

import java.util.List;

public interface MemberTotalStatRecordService {

    List<MemberTotalStatRecord> queryMemberTotalStatRecords(Long mid, Integer last_count, Long start_ts, Long end_ts,
                                                            Integer pn, Integer ps);

    Long queryMemberTotalStatRecordsCount(Long mid, Long start_ts, Long end_ts);
}
