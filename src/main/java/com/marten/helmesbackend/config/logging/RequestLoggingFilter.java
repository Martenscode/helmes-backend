package com.marten.helmesbackend.config.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("[REQUEST] " + getRequestAttributes(request));
        filterChain.doFilter(request, response);
        logger.info("[RESPONSE] " + getResponseAttributes(response));
    }

    private String getRequestAttributes(HttpServletRequest req) {
        String method = req.getMethod();
        String uri = req.getRequestURI();
        String userAgent = req.getHeader("User-Agent");
        String ip = req.getRemoteAddr();
        String host = req.getServerName();
        String port = String.valueOf(req.getServerPort());
        return String.format("URI=%s, Method=%s, User-Agent=%s, IP=%s, Host=%s, Port=%s", uri, method, userAgent, ip, host, port);
    }

    private String getResponseAttributes(HttpServletResponse res) {
        String status = String.valueOf(res.getStatus());
        return String.format("{HTTP %s}", status);
    }

}
