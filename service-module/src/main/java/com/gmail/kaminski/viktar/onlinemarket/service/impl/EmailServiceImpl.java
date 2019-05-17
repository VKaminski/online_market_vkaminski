package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String marketEmail;
    private JavaMailSender emailSender;

    private EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String addressee, String title, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(addressee);
        message.setSubject(title);
        message.setText(content);
        message.setFrom(marketEmail);
        emailSender.send(message);
    }
}
