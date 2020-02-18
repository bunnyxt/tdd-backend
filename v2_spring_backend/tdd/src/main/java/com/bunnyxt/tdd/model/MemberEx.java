package com.bunnyxt.tdd.model;

import com.bunnyxt.tdd.model.fragment.MemberFollowerRecordFragment;
import com.bunnyxt.tdd.model.fragment.MemberTotalStatRecordFragment;
import com.bunnyxt.tdd.model.fragment.VideoFragment;

public class MemberEx extends Member {

    private Integer video_count;
    private VideoFragment last_video;
    private MemberTotalStatRecordFragment last_total_stat;
    private MemberFollowerRecordFragment last_follower;

    public Integer getVideo_count() {
        return video_count;
    }

    public void setVideo_count(Integer video_count) {
        this.video_count = video_count;
    }

    public VideoFragment getLast_video() {
        return last_video;
    }

    public void setLast_video(VideoFragment last_video) {
        this.last_video = last_video;
    }

    public MemberTotalStatRecordFragment getLast_total_stat() {
        return last_total_stat;
    }

    public void setLast_total_stat(MemberTotalStatRecordFragment last_total_stat) {
        this.last_total_stat = last_total_stat;
    }

    public MemberFollowerRecordFragment getLast_follower() {
        return last_follower;
    }

    public void setLast_follower(MemberFollowerRecordFragment last_follower) {
        this.last_follower = last_follower;
    }
}
