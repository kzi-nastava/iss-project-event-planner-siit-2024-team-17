package com.ftn.event_hopper.dtos;

import com.ftn.event_hopper.models.shared.CommentStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateCommentDTO {
    private CommentStatus status;
}
