package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoRecordHourly;

import java.util.List;

public interface VideoRecordHourlyService {

    List<VideoRecordHourly> queryVideoRecordHourlys(String bvid, Integer last_count,
                                                    Integer start_ts, Integer end_ts, Integer pn, Integer ps);

    Integer queryVideoRecordHourlysCount(String bvid, Integer start_ts, Integer end_ts);
}
