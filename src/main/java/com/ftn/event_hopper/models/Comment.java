package com.ftn.event_hopper.models;

import com.ftn.event_hopper.models.shared.CommentStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Comment {

    private UUID id;
    private String content;
    private CommentStatus status;
    private EventOrganizer author;
}
