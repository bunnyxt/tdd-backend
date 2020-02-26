package com.bunnyxt.tdd.auth;

import com.bunnyxt.tdd.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ref: https://www.jianshu.com/p/5b660b32d96d

@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        response.getWriter().write("{\"code\":20001,\"message\":\"login success\",\"detail\":{" +
                "\"id\":" + ((User)authentication.getPrincipal()).getId() + "," +
                "\"added\":" + ((User)authentication.getPrincipal()).getAdded() + "," +
                "\"username\":\"" + authentication.getName() + "\"," +
                "\"enabled\":" + ((User)authentication.getPrincipal()).isEnabled() + "," +
                "\"nickname\":\"" + ((User)authentication.getPrincipal()).getNickname() + "\"," +
                "\"email\":\"" + ((User)authentication.getPrincipal()).getEmail() + "\"," +
                "\"phone\":\"" + ((User)authentication.getPrincipal()).getPhone() + "\"" +
                "}}");
    }
}
