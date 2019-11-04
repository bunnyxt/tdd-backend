package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Video;

import java.util.List;

public interface VideoService {

    Video queryVideoByAid(int aid);

    List<Video> queryVideosByTid(int tid, int pn, int ps);

    List<Video> queryVideosVc(int pn, int ps);

}
