package com.bunnyxt.tdd;

public class ApiResult {

    private int code;
    private String message;
    private Object data;

    // success
    public ApiResult(Object data) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.data = data;
    }

    // error
    public ApiResult(ErrorCode errorCode, String extraMessage) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage() + " " + extraMessage;
        this.data = null;
    }

    public ApiResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
