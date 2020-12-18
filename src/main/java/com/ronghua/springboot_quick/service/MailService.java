package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.config.MailConfiguration;
import com.ronghua.springboot_quick.entity.SendMailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {
    private Properties properties;
    private Authenticator authenticator;
    private InternetAddress internetAddress;


    public MailService(Properties properties, Authenticator authenticator, InternetAddress internetAddress) {
        this.properties = properties;
        this.authenticator = authenticator;
        this.internetAddress = internetAddress;
    }

    public void sendMail(SendMailRequest request) {

        Session session = Session.getInstance(properties, authenticator);
        try {
            Message message = new MimeMessage(session);
            message.setSubject(request.getSubject());
            message.setContent(request.getContent(), "text/html; charset=UTF-8");
            message.setFrom(internetAddress);
            for (String address : request.getReceivers()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
            }

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
