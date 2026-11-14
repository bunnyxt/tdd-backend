package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordService {

    List<VideoRecord> queryVideoRecords(Long aid, Integer last_count, Long start_ts, Long end_ts,
                                        Boolean limit, Integer pn, Integer ps);

    Long queryVideoRecordsCount(Long aid, Long start_ts, Long end_ts);

}
