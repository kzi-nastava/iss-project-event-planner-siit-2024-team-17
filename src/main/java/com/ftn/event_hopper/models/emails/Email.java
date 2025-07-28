package com.ftn.event_hopper.models.emails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {
    private String to;
    private String subject;
    private String body;
    private String attachment;
    private UUID invitationId;          //only if needed

    public Email(String to, String subject, String body, String attachment) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
    }

}
