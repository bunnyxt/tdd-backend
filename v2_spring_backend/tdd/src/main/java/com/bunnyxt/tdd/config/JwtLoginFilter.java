package com.bunnyxt.tdd.config;

import com.bunnyxt.tdd.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;


public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    protected JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
            throws AuthenticationException, IOException, ServletException {
        User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            roles.append(authority.getAuthority()).append(",");
        }
        String jwt = Jwts.builder()
                .claim("roles", roles.toString()) //roles
                .setSubject(authResult.getName()) // username
                .setIssuedAt(new Date(System.currentTimeMillis())) // isa
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // exp ts 10 days later
                .signWith(SignatureAlgorithm.HS512, "testkey")
                .compact();
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(jwt));
        out.flush();
        out.close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(401);
        PrintWriter out = resp.getWriter();
        out.write("{");
        out.write("\"code\":40101,");
        out.write("\"message\":\"invalid username or password\",");
        out.write("\"detail\":{}");
        out.write("}");
        out.flush();
        out.close();
    }
}