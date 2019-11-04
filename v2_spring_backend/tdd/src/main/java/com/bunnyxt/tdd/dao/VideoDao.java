package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Video;

import java.util.List;

public interface VideoDao {

    Video queryVideoByAid(int aid);

    List<Video> queryVideosByTid(int tid, int offset, int ps);

    List<Video> queryVideosVc(int offset, int ps);

}
