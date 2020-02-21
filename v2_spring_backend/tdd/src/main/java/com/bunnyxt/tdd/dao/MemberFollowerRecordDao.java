package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberFollowerRecord;

import java.util.List;

public interface MemberFollowerRecordDao {

    List<MemberFollowerRecord> queryMemberFollowerRecords(Integer mid, Integer last_count, Integer start_ts, Integer end_ts,
                                                          Integer offset, Integer ps);

    Integer queryMemberFollowerRecordsCount(Integer mid, Integer start_ts, Integer end_ts);
}
