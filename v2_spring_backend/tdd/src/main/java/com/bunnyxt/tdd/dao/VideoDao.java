package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Video;

public interface VideoDao {

    void updateVideoByAid(int aid, Video video);

    Video queryVideoByAid(int aid);

}
