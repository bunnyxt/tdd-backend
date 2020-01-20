package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Video;

public interface VideoService {

    void updateVideoByAid(Integer aid, Video video);

    Video queryVideoByAid(Integer aid);

}
