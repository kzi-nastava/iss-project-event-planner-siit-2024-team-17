package com.ftn.event_hopper.models.messages;

import com.ftn.event_hopper.models.users.Person;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Message {
    private UUID id;
    private String content;
    private LocalDateTime timestamp;
    private Person to;
    private Person from;
}
