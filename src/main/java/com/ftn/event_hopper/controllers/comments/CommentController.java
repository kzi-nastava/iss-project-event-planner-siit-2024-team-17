package com.ftn.event_hopper.controllers.comments;


import com.ftn.event_hopper.dtos.comments.*;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedCommentDTO> createComment(@RequestBody CreateCommentDTO comment){

        CreatedCommentDTO createdComment = new CreatedCommentDTO();

        return  new ResponseEntity<CreatedCommentDTO>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedCommentDTO> updateComment(@PathVariable UUID id, @RequestBody UpdateCommentDTO comment) throws Exception{

        UpdatedCommentDTO updatedComment = new UpdatedCommentDTO();

        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);


    }
}
