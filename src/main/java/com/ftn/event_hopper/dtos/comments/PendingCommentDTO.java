package com.ftn.event_hopper.dtos.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingCommentDTO {
    private UUID id;
    private String content;
    private SimpleCommentAuthorDTO author;
    private String email;
}
