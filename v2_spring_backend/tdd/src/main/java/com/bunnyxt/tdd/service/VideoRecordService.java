package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordService {

    List<VideoRecord> queryVideoRecords(Long aid, Integer last_count, Integer start_ts, Integer end_ts,
                                        Boolean limit, Integer pn, Integer ps);

    Integer queryVideoRecordsCount(Long aid, Integer start_ts, Integer end_ts);

}
