package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.MemberTotalStatRecord;

import java.util.List;

public interface MemberTotalStatRecordDao {

    List<MemberTotalStatRecord> queryMemberTotalStatRecords(Long mid, Integer last_count, Integer start_ts, Integer end_ts,
                                                            Integer offset, Integer ps);

    Integer queryMemberTotalStatRecordsCount(Long mid, Integer start_ts, Integer end_ts);
}
