package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordDao {

    List<VideoRecord> queryVideoRecords(Long aid, Integer last_count, Long start_ts, Long end_ts, Boolean limit,
                                        Long offset, Integer ps);

    Long queryVideoRecordsCount(Long aid, Long start_ts, Long end_ts);

}
