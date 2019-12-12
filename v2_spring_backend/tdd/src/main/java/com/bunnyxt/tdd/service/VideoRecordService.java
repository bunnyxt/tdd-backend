package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecord;

import java.util.List;

public interface VideoRecordService {

    List<VideoRecord> queryVideoRecords(int aid, int start_ts, int end_ts, int pn, int ps);

    int queryVideoRecordsCount(int aid, int start_ts, int end_ts);

}
