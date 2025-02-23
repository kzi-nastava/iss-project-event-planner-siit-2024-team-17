package com.ftn.event_hopper.repositories.comments;

import com.ftn.event_hopper.dtos.comments.GetCommentDTO;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.shared.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByStatus(CommentStatus status);
}
