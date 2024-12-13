package com.ftn.event_hopper.controllers.emails;

import com.ftn.event_hopper.dtos.emails.CreatedEmailDTO;
import com.ftn.event_hopper.services.emails.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/emails")
@CrossOrigin (origins = "*")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody CreatedEmailDTO details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody CreatedEmailDTO details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CreatedEmailDTO> sendEmail(@RequestBody CreateEmailDTO email) {
//        CreatedEmailDTO emailDTO = emailService.send(email);
//        if (emailDTO == null) {
//            return new ResponseEntity<CreatedEmailDTO>(HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<CreatedEmailDTO>(emailDTO, HttpStatus.CREATED);
//    }

}
