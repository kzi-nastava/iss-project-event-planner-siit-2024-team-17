package com.ftn.event_hopper.dtos.messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChatMessageDTO {
    private String sender;
    private String recipient;
    private String content;
}
