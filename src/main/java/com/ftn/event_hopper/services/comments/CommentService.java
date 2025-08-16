package com.ftn.event_hopper.services.comments;

import com.ftn.event_hopper.dtos.comments.GetCommentDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.comments.UpdatedCommentDTO;
import com.ftn.event_hopper.dtos.notifications.CreateNotificationDTO;
import com.ftn.event_hopper.mapper.comments.CommentDTOMapper;
import com.ftn.event_hopper.models.comments.Comment;
import com.ftn.event_hopper.models.shared.CommentStatus;
import com.ftn.event_hopper.repositories.comments.CommentRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentDTOMapper commentDTOMapper;
    @Autowired
    private ProductRepository productRepository;

    public Collection<SimpleCommentDTO> findAllPending(){
        List<Comment> comments = commentRepository.findByStatus(CommentStatus.PENDING);
        return commentDTOMapper.fromCommentListToSimplecommentDTOCollection(comments);
    }


    public UpdatedCommentDTO approveComment(UUID id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            return null;
        }

        comment.setStatus(CommentStatus.ACCEPTED);
        commentRepository.save(comment);
        commentRepository.flush();

        CreateNotificationDTO notificationDTO = new CreateNotificationDTO(
                "You have new comment by " + comment.getAuthor().getName() + " " + comment.getAuthor().getSurname() + "!\n" +
                "Comment: " + comment.getContent(),
                null,
                productRepository.findByCommentsContaining(comment).getId()
        );

        return commentDTOMapper.fromCommentToUpdatedCommentDTO(comment);

    }

    public UpdatedCommentDTO deleteComment(UUID id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            return null;
        }

        comment.setStatus(CommentStatus.DENIED);
        commentRepository.save(comment);
        commentRepository.flush();

        return commentDTOMapper.fromCommentToUpdatedCommentDTO(comment);

    }
}
