package com.bunnyxt.tdd.model;

public class VideoRecord {

    private Long id;
    private Integer added;
    private Integer aid;
    private Integer view;
    private Integer danmaku;
    private Integer reply;
    private Integer favorite;
    private Integer coin;
    private Integer share;
    private Integer like;

    private Integer dislike;

    private Integer now_rank;

    private Integer his_rank;

    private Long vt;

    private Long vv;

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

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
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

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getNow_rank() {
        return now_rank;
    }

    public void setNow_rank(Integer now_rank) {
        this.now_rank = now_rank;
    }

    public Integer getHis_rank() {
        return his_rank;
    }

    public void setHis_rank(Integer his_rank) {
        this.his_rank = his_rank;
    }

    public Long getVt() {
        return vt;
    }

    public void setVt(Long vt) {
        this.vt = vt;
    }

    public Long getVv() {
        return vv;
    }

    public void setVv(Long vv) {
        this.vv = vv;
    }

}
