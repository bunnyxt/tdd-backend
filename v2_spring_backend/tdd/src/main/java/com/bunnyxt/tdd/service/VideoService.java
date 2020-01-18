package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Video;

public interface VideoService {

    void updateVideoByAid(int aid, Video video);

    Video queryVideoByAid(int aid);

}
