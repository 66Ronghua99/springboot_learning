package com.ronghua.springboot_quick.service;

import com.ronghua.springboot_quick.entity.mail.SendMailRequest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MailService {
    private Properties properties;
    private Authenticator authenticator;
    private InternetAddress internetAddress;
    private List<Message> mailMessages;
    private long timeStamp;


    public MailService(Properties properties, Authenticator authenticator, InternetAddress internetAddress) {
        this.properties = properties;
        this.authenticator = authenticator;
        this.internetAddress = internetAddress;
        mailMessages = new ArrayList<>();
        timeStamp = System.currentTimeMillis();
    }

    public void sendNewProductMail(String productId){
        sendMail("New Product", "There is a new product with ID: "+productId, "893304167@qq.com");
    }

    public void sendReplaceProductMail(String productId){
        sendMail("Modifying product", "Product with id:"+ productId + " is modified", "893304167@qq.com");
    }

    public void sendMail(String subject, String content, String receiver){
        sendMail(subject, content, Collections.singletonList(receiver));
    }

    public void sendMail(String subject, String content, List<String> receivers){
        SendMailRequest request = new SendMailRequest();
        request.setSubject(subject);
        request.setContent(content);
        request.setReceivers(receivers);
        sendMail(request);
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
            mailMessages.add(message);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
