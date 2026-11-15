package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberFollowerRecord;

import java.util.List;

public interface MemberFollowerRecordService {

    List<MemberFollowerRecord> queryMemberFollowerRecords(Long mid, Integer last_record, Long start_ts, Long end_ts,
                                                          Integer pn, Integer ps);

    Long queryMemberFollowerRecordsCount(Long mid, Long start_ts, Long end_ts);
}
