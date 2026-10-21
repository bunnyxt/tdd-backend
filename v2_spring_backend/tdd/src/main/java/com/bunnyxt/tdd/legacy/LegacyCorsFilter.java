package com.bunnyxt.tdd.legacy;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * The old endpoints always emit wildcard CORS, including requests without Origin.
 * Hide Origin only on these seven routes so the existing credentialed security/MVC
 * CORS processors cannot reject or rewrite their responses or consume OPTIONS.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class LegacyCorsFilter extends OncePerRequestFilter {
    private static final Set<String> PATHS = new HashSet<>(Arrays.asList(
            "/legacy/member", "/legacy/sprint_daily", "/legacy/sprint_daily_record",
            "/legacy/sprint_video", "/legacy/sprint_video_record",
            "/legacy/bapi_aid_bvid", "/legacy/bapi_view"));

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !PATHS.contains(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(new HttpServletRequestWrapper(request) {
            @Override public String getHeader(String name) {
                return "Origin".equalsIgnoreCase(name) ? null : super.getHeader(name);
            }
            @Override public Enumeration<String> getHeaders(String name) {
                return "Origin".equalsIgnoreCase(name) ? Collections.enumeration(Collections.<String>emptyList()) : super.getHeaders(name);
            }
            @Override public Enumeration<String> getHeaderNames() {
                List<String> names = Collections.list(super.getHeaderNames());
                names.removeIf("Origin"::equalsIgnoreCase);
                return Collections.enumeration(names);
            }
        }, response);
    }
}
