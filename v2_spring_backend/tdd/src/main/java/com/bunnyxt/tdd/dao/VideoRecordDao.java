package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordDao {

    List<VideoRecord> queryVideoRecords(Integer aid, Integer last_count, Integer start_ts, Integer end_ts, Boolean limit,
                                        Integer offset, Integer ps);

    Integer queryVideoRecordsCount(Integer aid, Integer start_ts, Integer end_ts);

}
