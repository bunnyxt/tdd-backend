package com.bunnyxt.tdd.model;

import com.bunnyxt.tdd.model.fragment.MemberFragment;
import com.bunnyxt.tdd.model.fragment.VideoRecordFragment;

public class VideoEx extends Video {

    private MemberFragment member;
    private VideoRecordFragment laststat;


    public VideoEx() {
        super();
    }

    public MemberFragment getMember() {
        return member;
    }

    public void setMember(MemberFragment member) {
        this.member = member;
    }

    public VideoRecordFragment getLaststat() {
        return laststat;
    }

    public void setLaststat(VideoRecordFragment laststat) {
        this.laststat = laststat;
    }
}
