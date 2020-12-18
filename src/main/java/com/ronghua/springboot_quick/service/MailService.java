package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.config.MailConfiguration;
import com.ronghua.springboot_quick.entity.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    MailConfiguration mailConfig;

    public void sendMail(SendMailRequest request) {
        // new to java.mail
//        final String host = "smtp.126.com";
//        final int port = 25;
//        final boolean enableAuth = true;
//        final boolean enableStarttls = true;
//        final String userAddress = "lironghua980303@126.com";
//        final String pwd = "RTMYNXJVQWJNWOBL";
//        final String userDisplayName = "Ronghua";
//

        //set values in application.properties
        final String host = mailConfig.getHost();
        final int port = mailConfig.getPort();
        final boolean enableAuth = mailConfig.isAuthEnabled();
        final boolean enableStarttls = mailConfig.isStarttlsEnabled();
        final String userAddress = mailConfig.getUserAddress();
        final String pwd = mailConfig.getUserPwd();
        final String userDisplayName = mailConfig.getUserDisplayName();

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", String.valueOf(enableAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(enableStarttls));

        Session session = Session.getInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(userAddress, pwd);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setSubject(request.getSubject());
            message.setContent(request.getContent(), "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress(userAddress, userDisplayName));
            for (String address : request.getReceivers()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            }

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
