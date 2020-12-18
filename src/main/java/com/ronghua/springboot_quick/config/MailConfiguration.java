package com.ronghua.springboot_quick.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "mail")//only ConfigurationProperties could reflect to those name with 'camel', '-' and '_'
//with prefix, we can define and match properties more precisely
@PropertySource(value = {"classpath:mail.properties"}) //default classpath would be application.properties, but could be set to mail.properties
public class MailConfiguration {
//    //使用value赋值
//    @Value("${mail.host}")
//    private String host;
//
//    @Value("${mail.port:25}") // 使用「:」符號可以加上預設值
//    private int port;
//
//    @Value("${mail.auth.enabled}")
//    private boolean authEnabled;
//
//    @Value("${mail.starttls.enabled}")
//    private boolean starttlsEnabled;
//
//    @Value("${mail.user.address}")
//    private String userAddress;
//
//    @Value("${mail.user.pwd}")
//    private String userPwd;
//
//    @Value("${mail.user.display_name}")
//    private String userDisplayName;

    private String host;
    private int port;
    private boolean authEnabled;
    private boolean starttlsEnabled;
    private String userAddress;
    private String userPwd;
    private String userDisplayName;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isAuthEnabled() {
        return authEnabled;
    }

    public boolean isStarttlsEnabled() {
        return starttlsEnabled;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAuthEnabled(boolean authEnabled) {
        this.authEnabled = authEnabled;
    }

    public void setStarttlsEnabled(boolean starttlsEnabled) {
        this.starttlsEnabled = starttlsEnabled;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }
}


