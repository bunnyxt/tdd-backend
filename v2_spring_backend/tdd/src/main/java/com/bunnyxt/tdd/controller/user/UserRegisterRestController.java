package com.bunnyxt.tdd.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bunnyxt.tdd.error.InvalidRequestParameterException;
import com.bunnyxt.tdd.model.TddCommonResponse;
import com.bunnyxt.tdd.service.user.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
public class UserRegisterRestController {

    @Autowired
    UserRegisterService userRegisterService;

    @RequestMapping(value = "/register/code", method = RequestMethod.POST)
    public TddCommonResponse requestCode(
            @RequestBody JSONObject jsonObject
    ) throws InvalidRequestParameterException {
        // get params
        String method = jsonObject.getString("method");
        String validation = jsonObject.getString("validation");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        // check format
        // method
        if (!"email".equals(method)) {
            throw new InvalidRequestParameterException("method", method, "method should be email");
        }
        // validation
        if (validation == null) {
            throw new InvalidRequestParameterException("validation", null, "validation should not be null");
        }
        String pattern = "";
        pattern = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
        if (!Pattern.matches(pattern, validation)) {
            throw new InvalidRequestParameterException("validation", validation, "invalid " + method + " format");
        }
        if (validation.length() > 200) {
            throw new InvalidRequestParameterException("validation", validation, "invalid " + method + " format, length of email is too long");
        }
        // username
        if (username == null) {
            throw new InvalidRequestParameterException("username", null, "username should not be null");
        }
        if (username.length() < 4 || username.length() > 16) {
            throw new InvalidRequestParameterException("username", username, "length of username should between 4 and 16");
        }
        pattern = "^[A-Za-z0-9_\\-]+$";
        if (!Pattern.matches(pattern, username)) {
            throw new InvalidRequestParameterException("username", username, "invalid character detected");
        }
        // password
        if (password == null) {
            throw new InvalidRequestParameterException("password", null, "password should not be null");
        }
        if (password.length() < 6 || password.length() > 16) {
            throw new InvalidRequestParameterException("password", password, "length of password should between 6 and 16");
        }
        pattern = "^[A-Za-z0-9!@#$%^&*()\\-=_+\\[\\]\\\\{}|;:'\",./<>?`~]+$";
        if (!Pattern.matches(pattern, password)) {
            throw new InvalidRequestParameterException("password", password, "invalid character detected");
        }
        List<String> patternList = new ArrayList<String>(){{
            add(".*[A-Z].*");
            add(".*[a-z].*");
            add(".*[0-9].*");
            add(".*[!@#$%^&*()\\-=_+\\[\\]\\\\{}|;:'\",./<>?`~].*");
        }};
        int pwLevel = 0;
        for (String p: patternList) {
            if (Pattern.matches(p, password)) {
                pwLevel++;
            }
        }
        if (password.length() > 10) {
            pwLevel++;
        }
        if (pwLevel < 2) {
            throw new InvalidRequestParameterException("password", password, "password too weak");
        }
        return userRegisterService.requestCode(method, validation, username, password);
    }

    @RequestMapping(value = "/register/reg", method = RequestMethod.POST)
    public TddCommonResponse goRegister(
            @RequestBody JSONObject jsonObject
    ) throws InvalidRequestParameterException {
        // get params
        String regkey = jsonObject.get("regkey").toString();
        String code = jsonObject.get("code").toString();

        // check params
        if (regkey == null) {
            throw new InvalidRequestParameterException("regkey", null, "regkey should not be null");
        }
        if (code == null) {
            throw new InvalidRequestParameterException("code", null, "code should not be null");
        }

        return userRegisterService.goRegister(regkey, code);
    }

}
