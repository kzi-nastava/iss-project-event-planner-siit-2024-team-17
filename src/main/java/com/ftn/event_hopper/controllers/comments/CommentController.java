package com.ftn.event_hopper.controllers.comments;


import com.ftn.event_hopper.dtos.comments.CreateCommentDTO;
import com.ftn.event_hopper.dtos.comments.CreatedCommentDTO;
import com.ftn.event_hopper.dtos.comments.SimpleCommentDTO;
import com.ftn.event_hopper.dtos.comments.UpdatedCommentDTO;
import com.ftn.event_hopper.services.comments.CommentService;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/pending" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleCommentDTO>> getPendingComments(){
        Collection<SimpleCommentDTO> comments = commentService.findAllPending();

        return new ResponseEntity<Collection<SimpleCommentDTO>>(comments, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComment(@RequestBody CreateCommentDTO comment){

        try {
            CreatedCommentDTO createdComment = productService.addComment(comment);

            if (createdComment == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
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
