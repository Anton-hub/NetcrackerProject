package com.vkgroupstat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FeedbackService {
    private MailSender mailSender;

    public FeedbackService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendMsg(String emailUser, String subject, String message) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("upbase2020nc@gmail.com");
        mail.setTo("upbase2020nc@gmail.com");
        mail.setSubject(subject);
        mail.setText(String.format("Answer to %s\n\n%s", emailUser, message));

        try {
            mailSender.send(mail);
        } catch (MailException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
        return "OK";
    }

    public boolean validateEmail(String emailUser) {
        Pattern pattern = Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailUser);
        return matcher.matches();
    }
}
