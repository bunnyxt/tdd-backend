package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoEx;
import com.bunnyxt.tdd.model.fragment.VideoFragment;

import java.util.List;

public interface VideoExDao {

    VideoEx queryVideoByAid(Integer aid);

    List<VideoEx> queryVideos(Integer vc, Integer start_ts, Integer end_ts, Integer activity, Integer recent,
                              String title, String up, String order_by, Integer desc,
                              Integer offset, Integer ps);

    Integer queryVideosCount(Integer vc, Integer start_ts, Integer end_ts, Integer activity, Integer recent,
                             String title, String up);

    List<VideoEx> queryVideosByMid(Long mid,
                                   String order_by, Integer desc, Integer offset, Integer ps);

    Integer queryVideosByMidCount(Long mid);

}
