package com.bunnyxt.tdd.auth;

import com.bunnyxt.tdd.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// ref: https://www.jianshu.com/p/5b660b32d96d

@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String []  allowDomain= {
                "http://localhost:8080",
                "http://tdd.bunnyxt.com", "https://tdd.bunnyxt.com",
                "http://tdd2.bunnyxt.com", "https://tdd2.bunnyxt.com"
        };
        Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader=((HttpServletRequest) request).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader); // TODO set response to multiple address
        } else {
            response.setHeader("Access-Control-Allow-Origin", "https://tdd.bunnyxt.com");
        }
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
