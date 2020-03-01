package com.bunnyxt.tdd.auth;

import com.bunnyxt.tdd.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ref: https://www.jianshu.com/p/5b660b32d96d

@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        response.getWriter().write("{\"code\":20001,\"message\":\"login success\",\"detail\":{" +
                "\"id\":" + ((User)authentication.getPrincipal()).getId() + "," +
                "\"added\":" + ((User)authentication.getPrincipal()).getAdded() + "," +
                "\"username\":\"" + authentication.getName() + "\"," +
                "\"nickname\":\"" + ((User)authentication.getPrincipal()).getNickname() + "\"," +
                "\"email\":\"" + ((User)authentication.getPrincipal()).getEmail() + "\"," +
                "\"phone\":\"" + ((User)authentication.getPrincipal()).getPhone() + "\"," +
                "\"point\":\"" + ((User)authentication.getPrincipal()).getPoint() + "\"," +
                "\"exp\":\"" + ((User)authentication.getPrincipal()).getExp() + "\"," +
                "\"roles\":" + JSONArray.toJSONString(((User)authentication.getPrincipal()).getRoles()) + "" +
                "}}");
    }
}
