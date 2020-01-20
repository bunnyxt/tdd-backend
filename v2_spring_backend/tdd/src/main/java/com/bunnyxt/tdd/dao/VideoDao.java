package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Video;

public interface VideoDao {

    void updateVideoByAid(Integer aid, Video video);

    Video queryVideoByAid(Integer aid);

}
