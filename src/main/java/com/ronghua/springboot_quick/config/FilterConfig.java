package com.ronghua.springboot_quick.config;

import com.ronghua.springboot_quick.filter.LogApiFilter;
import com.ronghua.springboot_quick.filter.LogProcessTimeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean logProcessTimeFilter(){
        FilterRegistrationBean<LogProcessTimeFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogProcessTimeFilter());
        bean.setName("logProcessTimeFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(2);
        return bean;
    }

    @Bean
    public FilterRegistrationBean logApiFilter(){
        FilterRegistrationBean<LogApiFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogApiFilter());
        bean.setName("logApiFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
