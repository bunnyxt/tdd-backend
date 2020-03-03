package com.bunnyxt.tdd.model.user;

import com.bunnyxt.tdd.model.VideoEx;

public class UserFavoriteVideoEx extends UserFavoriteVideo {

    private VideoEx video;

    public VideoEx getVideo() {
        return video;
    }

    public void setVideo(VideoEx video) {
        this.video = video;
    }
}
