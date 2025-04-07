package com.bunnyxt.tdd.dao;

import com.bunnyxt.tdd.model.Video;

public interface VideoDao {

    void updateVideoByAid(Long aid, Video video);

    Video queryVideoByAid(Long aid);

}
