package com.bunnyxt.tdd.auth;

// ref: https://www.jianshu.com/p/693914564406

public class AuthenticationBean {

    private String username;
    private String password;
    private String recaptcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(String recaptcha) {
        this.recaptcha = recaptcha;
    }
}
