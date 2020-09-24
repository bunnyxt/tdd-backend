package com.bunnyxt.tdd.model.video.record.rank;

public class WeeklyArchiveOverview {

    private Long id;
    private String name;
    private Integer start_ts;
    private Integer end_ts;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStart_ts() {
        return start_ts;
    }

    public void setStart_ts(Integer start_ts) {
        this.start_ts = start_ts;
    }

    public Integer getEnd_ts() {
        return end_ts;
    }

    public void setEnd_ts(Integer end_ts) {
        this.end_ts = end_ts;
    }
}
