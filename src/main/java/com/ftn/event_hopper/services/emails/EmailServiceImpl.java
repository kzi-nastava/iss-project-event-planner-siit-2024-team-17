package com.ftn.event_hopper.services.emails;

import com.ftn.event_hopper.dtos.emails.CreateEmailDTO;
import com.ftn.event_hopper.dtos.emails.CreatedEmailDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
    public CreatedEmailDTO send(CreateEmailDTO email) {
        return null;
    }
}
