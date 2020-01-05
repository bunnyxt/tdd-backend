package com.bunnyxt.tdd.model;

import com.bunnyxt.tdd.model.fragment.MemberFragment;
import com.bunnyxt.tdd.model.fragment.VideoRecordFragment;

import java.util.List;

public class VideoEx extends Video {

    private MemberFragment member;
    private VideoRecordFragment laststat;
    private List<VideoStaffEx> staff;


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


    public List<VideoStaffEx> getStaff() {
        return staff;
    }

    public void setStaff(List<VideoStaffEx> staff) {
        this.staff = staff;
    }
}
