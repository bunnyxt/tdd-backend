package com.bunnyxt.tdd.model;

public class VideoLog {

    private Long id;
    private Integer added;
    private Long aid;
    private String attr;
    private String oldval;
    private String newval;


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

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getOldval() {
        return oldval;
    }

    public void setOldval(String oldval) {
        this.oldval = oldval;
    }

    public String getNewval() {
        return newval;
    }

    public void setNewval(String newval) {
        this.newval = newval;
    }
}
