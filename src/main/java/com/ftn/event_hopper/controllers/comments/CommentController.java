package com.ftn.event_hopper.controllers.comments;


import com.ftn.event_hopper.dtos.comments.*;
import com.ftn.event_hopper.services.comments.CommentService;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetCommentDTO>> getComments(){

        Collection<GetCommentDTO> comments = new ArrayList<GetCommentDTO>();

        return new ResponseEntity<Collection<GetCommentDTO>>(comments, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCommentDTO> getComment(@PathVariable UUID id){
        GetCommentDTO comment = new GetCommentDTO();

        if (comment == null){
            return new ResponseEntity<GetCommentDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetCommentDTO>(comment, HttpStatus.OK);
    }

    @GetMapping(value = "/pending" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleCommentDTO>> getPendingComments(){
        Collection<SimpleCommentDTO> comments = commentService.findAllPending();

        return new ResponseEntity<Collection<SimpleCommentDTO>>(comments, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCommentDTO> createComment(@RequestBody CreateCommentDTO comment){

        CreatedCommentDTO createdComment = productService.addComment(comment);

        if (createdComment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<CreatedCommentDTO>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCommentDTO> updateComment(@PathVariable UUID id, @RequestBody UpdateCommentDTO comment) throws Exception{

        UpdatedCommentDTO updatedComment = new UpdatedCommentDTO();

        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);

    }

    @PutMapping(value = "/pending/{id}/approve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCommentDTO> approveComment(@PathVariable UUID id) {
        UpdatedCommentDTO updatedComment = commentService.approveComment(id);

        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);
    }

    @PutMapping(value = "/pending/{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCommentDTO> deleteComment(@PathVariable UUID id) {
        UpdatedCommentDTO updatedComment = commentService.deleteComment(id);

        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);
    }



}
