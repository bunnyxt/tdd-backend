package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoEx;

import java.util.List;

public interface VideoExService {

    VideoEx queryVideoByAid(Integer aid);

    List<VideoEx> queryVideos(Integer vc, Integer start_ts, Integer end_ts, String title, String up, String order_by, Integer desc,
                              Integer pn, Integer ps);

    Integer queryVideosCount(Integer vc, Integer start_ts, Integer end_ts, String title, String up);
}
