package com.bunnyxt.tdd.model;

public class MemberTotalStatRecord {

    private Long id;
    private Long added;
    private Long mid;
    private Integer video_count;
    private Long view;
    private Long danmaku;
    private Long reply;
    private Long favorite;
    private Long coin;
    private Long share;
    private Long like;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdded() {
        return added;
    }

    public void setAdded(Long added) {
        this.added = added;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Integer getVideo_count() {
        return video_count;
    }

    public void setVideo_count(Integer video_count) {
        this.video_count = video_count;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Long getDanmaku() {
        return danmaku;
    }

    public void setDanmaku(Long danmaku) {
        this.danmaku = danmaku;
    }

    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply = reply;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public Long getShare() {
        return share;
    }

    public void setShare(Long share) {
        this.share = share;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }
}
