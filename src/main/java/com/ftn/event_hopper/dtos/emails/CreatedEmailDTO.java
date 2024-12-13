package com.ftn.event_hopper.dtos.emails;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatedEmailDTO {
    private String to;
    private String subject;
    private String body;
    private String attachment;
}
