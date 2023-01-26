package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberFollowerRecord;

import java.util.List;

public interface MemberFollowerRecordDao {

    List<MemberFollowerRecord> queryMemberFollowerRecords(Long mid, Integer last_count, Integer start_ts, Integer end_ts,
                                                          Integer offset, Integer ps);

    Integer queryMemberFollowerRecordsCount(Long mid, Integer start_ts, Integer end_ts);
}
