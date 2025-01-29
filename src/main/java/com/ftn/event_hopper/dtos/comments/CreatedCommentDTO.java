package com.ftn.event_hopper.dtos.comments;

import com.ftn.event_hopper.models.shared.CommentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreatedCommentDTO {
    private UUID id;
    private String content;
    private LocalDateTime createdAt;
    private CommentStatus status;
}
