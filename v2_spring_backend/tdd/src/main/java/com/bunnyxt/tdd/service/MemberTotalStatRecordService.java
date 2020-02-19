package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberTotalStatRecord;

import java.util.List;

public interface MemberTotalStatRecordService {

    List<MemberTotalStatRecord> queryMemberTotalStatRecords(Integer mid, Integer start_ts, Integer end_ts,
                                                            Integer pn, Integer ps);

    Integer queryMemberTotalStatRecordsCount(Integer mid, Integer start_ts, Integer end_ts);
}
