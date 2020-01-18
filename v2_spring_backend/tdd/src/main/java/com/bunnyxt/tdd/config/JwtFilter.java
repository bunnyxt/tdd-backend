package com.bunnyxt.tdd.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, SignatureException {

        // get jwt token from header
        String jwtToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        if (jwtToken == null) {
            // not logged in
            // TODO
        } else {
            // parse jwt token
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey("testkey")
                        .parseClaimsJws(jwtToken.replace("Bearer", ""))
                        .getBody();
            } catch (SignatureException e) {
                // invalid token
                System.out.println(e);
            }

            if (claims == null) {
                // invalid token
                // TODO
            } else {
                // check exp
                Date exp = claims.getExpiration();
                Date now = new Date();
                if (now.compareTo(exp) > 0) {
                    // token expired
                    // TODO
                } else {
                    // assign auth
                    String username = claims.getSubject();
                    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("roles"));
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(token);

                    // TODO replace token if necessary
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}