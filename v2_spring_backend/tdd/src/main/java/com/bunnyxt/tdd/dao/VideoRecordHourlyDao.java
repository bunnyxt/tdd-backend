package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoRecordHourly;

import java.util.List;

public interface VideoRecordHourlyDao {

    List<VideoRecordHourly> queryVideoRecordHourlys(String bvid, Integer last_count, Integer start_ts, Integer end_ts,
                                                    Integer offset, Integer ps);

    Integer queryVideoRecordHourlysCount(String bvid, Integer start_ts, Integer end_ts);
}
