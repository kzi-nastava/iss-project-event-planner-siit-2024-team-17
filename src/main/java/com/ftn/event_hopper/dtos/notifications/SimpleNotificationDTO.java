package com.ftn.event_hopper.dtos.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleNotificationDTO {
    private String content;
    private LocalDateTime timestamp;

}
