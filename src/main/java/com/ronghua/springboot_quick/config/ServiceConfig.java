package com.ronghua.springboot_quick.config;

import com.ronghua.springboot_quick.dao.AppUserDao;
import com.ronghua.springboot_quick.dao.ProductDao;
import com.ronghua.springboot_quick.entity.app_user.AppUser;
import com.ronghua.springboot_quick.entity.auth.UserIdentity;
import com.ronghua.springboot_quick.service.AppUserService;
import com.ronghua.springboot_quick.service.JWTService;
import com.ronghua.springboot_quick.service.MailService;
import com.ronghua.springboot_quick.service.ProductService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
//@Configuration will let spring know that there might be several beans here. MailConfiguration is just one kind of its use
public class ServiceConfig {
    //@Bean would declare a Bean instance in the IOC container. 5 scopes of Bean, default is singleton.
    //At the beginning of Spring, this function would be called and its returning value be put in IOC container
//    @Bean
//    public ProductService productService(ProductDao repository) {
//        return new ProductService(repository);
//    }
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ProductService productService(ProductDao productDao, MailService mailService, UserIdentity userIdentity){
        return new ProductService(productDao, mailService, userIdentity);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AppUserService appUserService(AppUserDao appUserDao, JWTService jwtService){
        return new AppUserService(appUserDao, jwtService);
    }
}
