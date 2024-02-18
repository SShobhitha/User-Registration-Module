package com.insurance.registration.utils;

import com.netflix.discovery.converters.Auto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;


    public boolean sendEmail(String to, String subject, String body) {
        boolean isSent = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            mailSender.send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSent;
    }
}
