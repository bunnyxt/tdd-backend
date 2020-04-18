package com.bunnyxt.tdd.model;

public class VideoRecordHourly {

    private Integer added;
    private String bvid;
    private Integer view;
    private Integer danmaku;
    private Integer reply;
    private Integer favorite;
    private Integer coin;
    private Integer share;
    private Integer like;

    public Integer getAdded() {
        return added;
    }

    public void setAdded(Integer added) {
        this.added = added;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Integer danmaku) {
        this.danmaku = danmaku;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
