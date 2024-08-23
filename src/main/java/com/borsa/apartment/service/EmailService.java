package com.borsa.apartment.service;

import com.borsa.apartment.model.Email;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Async
    public void sendEmail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("alpdev3@gmail.com", "ApartmentHunterHR");
            helper.setTo(email.getReceiver());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);

            LOGGER.info("Successfully sent email to: {}", email.getReceiver());
            mailSender.send(message);
        } catch (Exception e) {
           LOGGER.error("Failed to send email to: {}.", email.getReceiver());
        }
    }
}
