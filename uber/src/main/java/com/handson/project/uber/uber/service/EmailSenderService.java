package com.handson.project.uber.uber.service;


public interface EmailSenderService {

    public void sendEmail(String toEmail, String subject, String body);
}
