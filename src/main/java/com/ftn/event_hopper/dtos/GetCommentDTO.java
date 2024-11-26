package com.ftn.event_hopper.dtos;

import com.ftn.event_hopper.models.EventOrganizer;
import com.ftn.event_hopper.models.shared.CommentStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GetCommentDTO {

    private UUID id;
    private String content;
    private CommentStatus status;
    private UUID author;

}
