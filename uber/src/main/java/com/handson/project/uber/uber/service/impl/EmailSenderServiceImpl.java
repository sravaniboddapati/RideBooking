package com.handson.project.uber.uber.service.impl;

import com.handson.project.uber.uber.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
    try {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setReplyTo(toEmail);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }catch (Exception e){
        log.info("Exception while sending mail "+e.getMessage());
    }

    }
}
