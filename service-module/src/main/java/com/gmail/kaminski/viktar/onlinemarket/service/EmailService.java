package com.gmail.kaminski.viktar.onlinemarket.service;

public interface EmailService {
    void sendEmail(String addressee, String title, String content);
}
