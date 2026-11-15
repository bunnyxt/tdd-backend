package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberFollowerRecord;

import java.util.List;

public interface MemberFollowerRecordDao {

    List<MemberFollowerRecord> queryMemberFollowerRecords(Long mid, Integer last_count, Long start_ts, Long end_ts,
                                                          Long offset, Integer ps);

    Long queryMemberFollowerRecordsCount(Long mid, Long start_ts, Long end_ts);
}
