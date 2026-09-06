package com.bunnyxt.tdd.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

// ref: https://www.jianshu.com/p/693914564406

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request, HttpServletResponse response) {
        //use jackson to deserialize json
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = request.getInputStream()) {
            AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);

            // check username and password
            String username = authenticationBean.getUsername();
            String password = authenticationBean.getPassword(); // TODO encrypt password
            if (username == null) {
                request.setAttribute("type", "username");
                request.setAttribute("username", "username required");
                return new UsernamePasswordAuthenticationToken(
                        "", "");
            }
            if (password == null) {
                request.setAttribute("type", "password");
                request.setAttribute("password", "password required");
                return new UsernamePasswordAuthenticationToken(
                        "", "");
            }

            return new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            return new UsernamePasswordAuthenticationToken(
                    "", "");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // attempt Authentication when Content-Type is json, POST method only
        if (request.getMethod().equals("POST") &&
                (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))) {

            UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request, response);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

        // transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }

}
