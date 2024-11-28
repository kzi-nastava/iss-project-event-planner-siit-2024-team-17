package com.ftn.event_hopper.dtos.comments;

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
    private UUID authorId;
    private LocalDateTime createdAt;
}
