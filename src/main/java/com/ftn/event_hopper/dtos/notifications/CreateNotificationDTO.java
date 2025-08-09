package com.ftn.event_hopper.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationDTO {
    private String content;
    private UUID eventId;
    private UUID productId;
}
