package com.ftn.event_hopper.services.emails;

import com.ftn.event_hopper.dtos.emails.CreatedEmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    // To send a simple email
    public String sendSimpleMail(CreatedEmailDTO email)
    {

        try {

            SimpleMailMessage mailMessage= new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getTo());
            mailMessage.setText(email.getBody());
            mailMessage.setSubject(email.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            return e.getMessage();
        }
    }

    // To send an email with attachment
    public String
    sendMailWithAttachment(CreatedEmailDTO email) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setText(email.getBody());
            mimeMessageHelper.setSubject(
                    email.getSubject());

            FileSystemResource file = new FileSystemResource(new File(email.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
}
