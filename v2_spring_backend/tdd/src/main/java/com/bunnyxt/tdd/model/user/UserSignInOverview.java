package com.bunnyxt.tdd.model.user;

public class UserSignInOverview {

    private Long id;
    private Long userid;
    private Integer total;
    private Integer last_added;
    private Integer last_added_days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLast_added() {
        return last_added;
    }

    public void setLast_added(Integer last_added) {
        this.last_added = last_added;
    }

    public Integer getLast_added_days() {
        return last_added_days;
    }

    public void setLast_added_days(Integer last_added_days) {
        this.last_added_days = last_added_days;
    }
}
