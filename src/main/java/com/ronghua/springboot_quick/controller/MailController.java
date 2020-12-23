package com.ronghua.springboot_quick.controller;

import com.ronghua.springboot_quick.config.MailConfiguration;
import com.ronghua.springboot_quick.entity.SendMailRequest;
import com.ronghua.springboot_quick.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<Void> sendMail(@Valid @RequestBody SendMailRequest request) {
        mailService.sendMail(request);
        System.out.println("Sending E-mail from Spring framework, Service instance: "+ mailService.toString());
        return ResponseEntity.noContent().build();
    }

}
