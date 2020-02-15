package com.bunnyxt.tdd.model;

import com.bunnyxt.tdd.model.fragment.VideoFragment;

public class MemberEx extends Member {

    private Integer videoCount;
    private Integer videoViewCount;
    private VideoFragment latestIssuedVideo;
    private VideoFragment mostViewedVideo;

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getVideoViewCount() {
        return videoViewCount;
    }

    public void setVideoViewCount(Integer videoViewCount) {
        this.videoViewCount = videoViewCount;
    }

    public VideoFragment getLatestIssuedVideo() {
        return latestIssuedVideo;
    }

    public void setLatestIssuedVideo(VideoFragment latestIssuedVideo) {
        this.latestIssuedVideo = latestIssuedVideo;
    }

    public VideoFragment getMostViewedVideo() {
        return mostViewedVideo;
    }

    public void setMostViewedVideo(VideoFragment mostViewedVideo) {
        this.mostViewedVideo = mostViewedVideo;
    }
}
