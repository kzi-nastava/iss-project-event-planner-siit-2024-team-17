package com.ftn.event_hopper.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleNotificationDTO {
    private String content;
    private LocalDateTime timestamp;
    private UUID productID;
    private UUID eventID;

}
