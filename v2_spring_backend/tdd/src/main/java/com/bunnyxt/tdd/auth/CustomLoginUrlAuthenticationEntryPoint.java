package com.bunnyxt.tdd.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ref: https://www.jianshu.com/p/ddbf8301b4b9

public class CustomLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"code\":40102,\"message\":\"not logged in\",\"detail\":{}}");
    }
}
