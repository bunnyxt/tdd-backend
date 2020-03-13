package com.bunnyxt.tdd.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ref: https://www.jianshu.com/p/5b660b32d96d

@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080"); // TODO set response to multiple address
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write("{\"code\":40101,\"message\":\"login failure\",\"detail\":");
        if (request.getAttribute("type") == null) {
            response.getWriter().write("{}}");
        } else {
            response.getWriter().write("{\"type\":\"" + request.getAttribute("type") + "\"");

            // check different type of failure
            if (request.getAttribute("username") != null) {
                response.getWriter().write(",\"username\":\"" + request.getAttribute("username") + "\"}}");
            }

            if (request.getAttribute("password") != null) {
                response.getWriter().write(",\"password\":\"" + request.getAttribute("password") + "\"}}");
            }

            if (request.getAttribute("recaptcha") != null) {
                response.getWriter().write(",\"recaptcha\":\"" + request.getAttribute("recaptcha") + "\"");
                if (request.getAttribute("error-codes") != null) {
                    response.getWriter().write(",\"error-codes\":\"" +
                            ((String) request.getAttribute("error-codes")).replace("\"", "") + "\"");
                }
                response.getWriter().write("}}");
            }
        }
    }
}
