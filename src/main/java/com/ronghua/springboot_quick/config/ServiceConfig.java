package com.ronghua.springboot_quick.config;

import com.ronghua.springboot_quick.dao.ProductDao;
import com.ronghua.springboot_quick.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Configuration will let spring know that there might be several beans here. MailConfiguration is just one kind of its use
public class ServiceConfig {
    //@Bean would declare a Bean instance in the IOC container. 5 scopes of Bean, default is singleton.
    //At the beginning of Spring, this function would be called and its returning value be put in IOC container
    @Bean
    public ProductService productService(ProductDao repository) {
        return new ProductService(repository);
    }

}
