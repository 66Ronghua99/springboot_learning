package com.ronghua.springboot_quick.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class LogApiFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("ApiFilter captured!");
//        BufferedReader bufferedReader = httpServletRequest.getReader(); //InputStream would only help get request body
//        String line = "";                                               //To prevent data being read by filter, we should use wrapper to protect the data in the stream
//        StringBuilder input = new StringBuilder();
//        while((line = bufferedReader.readLine()) != null){
//                input.append(line);
//        }
//        System.out.println("Request: " + input.toString());
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//        int httpStatus = httpServletResponse.getStatus();
//        String httpMethod = httpServletRequest.getMethod();
//        String httpUrl =  httpServletRequest.getRequestURI();
//        String params = httpServletRequest.getQueryString();
//        if(params != null){
//            httpUrl += "?" + params;
//        }
//        System.out.println(String.join(" ", String.valueOf(httpStatus), httpMethod, httpUrl));

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
        filterChain.doFilter(requestWrapper, responseWrapper);

        logAPI(httpServletRequest,httpServletResponse);
        logBody(requestWrapper, responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void logAPI(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        int httpStatus = httpServletResponse.getStatus();
        String httpMethod = httpServletRequest.getMethod();
        String httpUrl =  httpServletRequest.getRequestURI();
        String params = httpServletRequest.getQueryString();
        if(params != null){
            httpUrl += "?" + params;
        }
        System.out.println(String.join(" ", String.valueOf(httpStatus), httpMethod, httpUrl));
    }

    private void logBody(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        String requestBody = getContent(request.getContentAsByteArray());
        System.out.println("Request: " + requestBody);

        String responseBody = getContent(response.getContentAsByteArray());
        System.out.println("Response: " + responseBody);
    }

    private String getContent(byte[] content) {
        String body = new String(content);
        return body.replaceAll("[\n\t]", "");
    }
}
