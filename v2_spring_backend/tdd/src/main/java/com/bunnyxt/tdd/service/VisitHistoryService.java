package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VisitHistoryVideoEx;

import java.util.List;

public interface VisitHistoryService {

    void addVisitHistoryVideo(Long userid, String bvid);

    List<VisitHistoryVideoEx> getVisitHistoryVideoByUserid(Long userid, Integer start_ts, Integer end_ts,
                                                           Integer desc, Integer pn, Integer ps);

    Integer getVisitHistoryVideoCountByUserid(Long userid, Integer start_ts, Integer end_ts);
}
