package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.model.fragment.VideoFragment;

import java.util.List;

public interface VideoExDao {

    VideoEx queryVideoByAid(Long aid);

    List<VideoEx> queryVideos(Integer vc, Long start_ts, Long end_ts, Integer activity, Integer recent,
                              String title, String up, String order_by, Integer desc,
                              Long offset, Integer ps);

    Long queryVideosCount(Integer vc, Long start_ts, Long end_ts, Integer activity, Integer recent,
                             String title, String up);

    List<VideoEx> queryVideosByMid(Long mid,
                                   String order_by, Integer desc, Long offset, Integer ps);

    Long queryVideosByMidCount(Long mid);

}
