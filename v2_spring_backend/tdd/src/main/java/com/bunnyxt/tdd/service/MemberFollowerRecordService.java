package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.MemberFollowerRecord;

import java.util.List;

public interface MemberFollowerRecordService {

    List<MemberFollowerRecord> queryMemberFollowerRecords(Integer mid, Integer last_record, Integer start_ts, Integer end_ts,
                                                          Integer pn, Integer ps);

    Integer queryMemberFollowerRecordsCount(Integer mid, Integer start_ts, Integer end_ts);
}
