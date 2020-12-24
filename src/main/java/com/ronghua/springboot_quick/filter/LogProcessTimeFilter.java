package com.ronghua.springboot_quick.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogProcessTimeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("ProcessTimeFilter captured");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Process time: " + String.valueOf(duration));
    }
}
