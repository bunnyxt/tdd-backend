package com.bunnyxt.tdd.service;

import com.bunnyxt.tdd.model.Video;

public interface VideoService {

    void updateVideoByAid(Long aid, Video video);

    Video queryVideoByAid(Long aid);

}
