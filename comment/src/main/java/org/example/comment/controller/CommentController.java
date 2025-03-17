package org.example.comment.controller;


import lombok.AllArgsConstructor;
import org.example.comment.entity.Comment;
import org.example.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping(path="/comments")
    public List<Comment> comment() {
        return commentService.getAllComments();
    }

    @PostMapping(path="/comment")
    public ResponseEntity<?> addComment(@RequestHeader("X-Username") String username, @RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment, username));
    }

}
