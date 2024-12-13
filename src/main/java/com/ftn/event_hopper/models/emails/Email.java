package com.ftn.event_hopper.models.emails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {
    private String to;
    private String subject;
    private String body;
    private String attachment;

}
