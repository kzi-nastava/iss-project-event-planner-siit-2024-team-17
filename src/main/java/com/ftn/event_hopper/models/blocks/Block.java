package com.ftn.event_hopper.models.blocks;

import com.ftn.event_hopper.models.users.Person;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Block {
    private UUID id;
    private LocalDateTime timestamp;
    private Person who;
    private Person blocked;
}
