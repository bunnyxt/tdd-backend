package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.VideoEx;

import java.util.List;

public interface VideoExService {

    VideoEx queryVideoByAid(int aid);

    List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up, int pn, int ps);

    int queryVideosCount(int vc, int start_ts, int end_ts, String title, String up);
}
