package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VisitHistoryVideoEx;

import java.util.List;

public interface VisitHistoryDao {

    void addVisitHistoryVideo(Integer added, Long userid, String bvid);

    List<VisitHistoryVideoEx> getVisitHistoryVideoByUserid(Long userid, Integer start_ts, Integer end_ts,
                                                           Integer desc, Integer offset, Integer ps);

    Integer getVisitHistoryVideoCountByUserid(Long userid, Integer start_ts, Integer end_ts);
}
