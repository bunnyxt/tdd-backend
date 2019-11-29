package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.VideoEx;

import java.util.List;

public interface VideoExDao {

    VideoEx queryVideoByAid(int aid);

    List<VideoEx> queryVideos(int vc, int start_ts, int end_ts, String title, String up, int offset, int ps);

    int queryVideosCount(int vc, int start_ts, int end_ts, String title, String up);
}
