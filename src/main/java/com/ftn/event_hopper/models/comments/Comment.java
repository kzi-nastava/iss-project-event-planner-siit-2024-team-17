package com.ftn.event_hopper.models.comments;

import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.shared.CommentStatus;
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
