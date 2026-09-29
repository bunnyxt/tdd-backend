package com.bunnyxt.tdd.model;

public class AidRange {

    private Long from;
    private Long to;

    public AidRange(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }
}
