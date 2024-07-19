package org.example.crud_template.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class IPWhitelistFilter extends OncePerRequestFilter {

    private final List<String> whitelistedIPs;

    public IPWhitelistFilter(List<String> whitelistedIPs) {
        this.whitelistedIPs = whitelistedIPs;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIP = request.getRemoteAddr();
        if (!whitelistedIPs.contains(clientIP)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "IP address not allowed");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
