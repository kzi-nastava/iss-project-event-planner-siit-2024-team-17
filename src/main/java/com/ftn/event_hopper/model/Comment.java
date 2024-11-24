package com.ftn.event_hopper.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Comment {
    private UUID id;
    private String content;
    private EventOrganizer eventOrganizer;
    private CommentStatus status;
}
