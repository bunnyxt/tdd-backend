package com.bunnyxt.tdd.model;

public class StatDaily {

    private Long id;
    private Integer added;
    private Long video_count;
    private Long member_count;
    private Long video_record_count;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAdded() {
        return added;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public Long getVideo_count() {
        return video_count;
    }

    public void setVideo_count(Long video_count) {
        this.video_count = video_count;
    }

    public Long getMember_count() {
        return member_count;
    }

    public void setMember_count(Long member_count) {
        this.member_count = member_count;
    }

    public Long getVideo_record_count() {
        return video_record_count;
    }

    public void setVideo_record_count(Long video_record_count) {
        this.video_record_count = video_record_count;
    }
}
