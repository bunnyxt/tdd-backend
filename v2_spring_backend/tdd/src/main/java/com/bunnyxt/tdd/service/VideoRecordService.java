package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordService {

    List<VideoRecord> queryVideoRecords(Integer aid, Integer start_ts, Integer end_ts,
                                        Boolean limit, Integer pn, Integer ps);

    Integer queryVideoRecordsCount(Integer aid, Integer start_ts, Integer end_ts);

}
