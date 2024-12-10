package com.ftn.event_hopper.dtos.users.account;

import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SimpleAccountDTO {
    private UUID id;
    private String email;
    private PersonType type;
    private SimplePersonDTO person;
}

