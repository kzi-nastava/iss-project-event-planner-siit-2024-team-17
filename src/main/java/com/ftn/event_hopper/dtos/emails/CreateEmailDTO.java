package com.ftn.event_hopper.dtos.emails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEmailDTO {
    private String to;
    private String subject;
}
