package com.ftn.event_hopper.dtos.comments;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateCommentDTO {
    private String content;
    private UUID productId;
}
