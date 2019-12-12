package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordDao {

    List<VideoRecord> queryVideoRecords(int aid, int start_ts, int end_ts, int offset, int ps);

    int queryVideoRecordsCount(int aid, int start_ts, int end_ts);

}
