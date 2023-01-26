package com.bunnyxt.tdd.model.fragment;

public class VideoFragment {

    private Integer aid;
    private String pic;
    private String title;
    private Integer pubdate;
    private Long mid;
    private VideoRecordFragment laststat;


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPubdate() {
        return pubdate;
    }

    public void setPubdate(Integer pubdate) {
        this.pubdate = pubdate;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public VideoRecordFragment getLaststat() {
        return laststat;
    }

    public void setLaststat(VideoRecordFragment laststat) {
        this.laststat = laststat;
    }
}
