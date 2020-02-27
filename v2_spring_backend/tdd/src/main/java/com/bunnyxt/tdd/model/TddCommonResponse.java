package com.bunnyxt.tdd.model;

import java.util.Map;

public class TddCommonResponse {

    private String status;
    private String message;
    private Map<String, Object> detail;

    public TddCommonResponse() {

    }

    public TddCommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public TddCommonResponse(String status, String message, Map<String, Object> detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }
}
