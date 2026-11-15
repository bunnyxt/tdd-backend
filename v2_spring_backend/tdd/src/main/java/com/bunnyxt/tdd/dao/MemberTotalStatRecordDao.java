package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberTotalStatRecord;

import java.util.List;

public interface MemberTotalStatRecordDao {

    List<MemberTotalStatRecord> queryMemberTotalStatRecords(Long mid, Integer last_count, Long start_ts, Long end_ts,
                                                            Long offset, Integer ps);

    Long queryMemberTotalStatRecordsCount(Long mid, Long start_ts, Long end_ts);
}
