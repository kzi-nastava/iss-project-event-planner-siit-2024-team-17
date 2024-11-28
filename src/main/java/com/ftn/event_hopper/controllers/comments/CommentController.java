package com.ftn.event_hopper.controllers.comments;


import com.ftn.event_hopper.dtos.comments.GetCommentDTO;
import com.ftn.event_hopper.dtos.comments.CreateCommentDTO;
import com.ftn.event_hopper.dtos.comments.CreatedCommentDTO;
import com.ftn.event_hopper.dtos.comments.UpdateCommentDTO;
import com.ftn.event_hopper.dtos.comments.UpdatedCommentDTO;
import com.ftn.event_hopper.models.shared.CommentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetCommentDTO>> getComments(){

        Collection<GetCommentDTO> comments = new ArrayList<GetCommentDTO>();

        GetCommentDTO comment1 = new GetCommentDTO();
        comment1.setId(UUID.randomUUID());
        comment1.setContent("Very nice");
        comment1.setStatus(CommentStatus.PENDING);
        comment1.setAuthor(UUID.randomUUID());

        GetCommentDTO comment2 = new GetCommentDTO();
        comment2.setId(UUID.randomUUID());
        comment2.setContent("Very nice");
        comment2.setStatus(CommentStatus.PENDING);
        comment2.setAuthor(UUID.randomUUID());

        comments.add(comment1);
        comments.add(comment2);
        return new ResponseEntity<Collection<GetCommentDTO>>(comments, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCommentDTO> getComment(@PathVariable UUID id){
        GetCommentDTO comment = new GetCommentDTO();

        if (comment == null){
            return new ResponseEntity<GetCommentDTO>(HttpStatus.NOT_FOUND);
        }

        comment.setId(id);
        comment.setContent("Very nice");
        comment.setStatus(CommentStatus.PENDING);
        comment.setAuthor(UUID.randomUUID());

        return new ResponseEntity<GetCommentDTO>(comment, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCommentDTO> createComment(@RequestBody CreateCommentDTO comment){

        CreatedCommentDTO createdComment = new CreatedCommentDTO();
        createdComment.setId(UUID.randomUUID());
        createdComment.setContent(comment.getContent());
        createdComment.setAuthorId(comment.getAuthorId());
        createdComment.setCreatedAt(LocalDateTime.now());

        return  new ResponseEntity<CreatedCommentDTO>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCommentDTO> updateComment(@PathVariable UUID id, @RequestBody UpdateCommentDTO comment) throws Exception{

        UpdatedCommentDTO updatedComment = new UpdatedCommentDTO();
        updatedComment.setId(id);
        updatedComment.setStatus(comment.getStatus());

        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);


    }
}
