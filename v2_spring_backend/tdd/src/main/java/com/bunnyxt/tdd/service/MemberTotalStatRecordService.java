package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberTotalStatRecord;

import java.util.List;

public interface MemberTotalStatRecordService {

    List<MemberTotalStatRecord> queryMemberTotalStatRecords(Long mid, Integer last_count, Integer start_ts, Integer end_ts,
                                                            Integer pn, Integer ps);

    Integer queryMemberTotalStatRecordsCount(Long mid, Integer start_ts, Integer end_ts);
}
