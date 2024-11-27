package com.ftn.event_hopper.dtos.comments;

import com.ftn.event_hopper.models.shared.CommentStatus;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdatedCommentDTO {
    private UUID id;
    private CommentStatus status;
}
