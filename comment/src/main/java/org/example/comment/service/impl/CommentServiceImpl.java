package org.example.comment.service.impl;

import lombok.AllArgsConstructor;
import org.example.comment.entity.Comment;
import org.example.comment.repository.CommentRepository;
import org.example.comment.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public String createComment(Comment comment, String username) {
        try {
            comment.setUsername(username);
            commentRepository.save(comment);
            return "Successfully created a comment";
        } catch (Exception e) {
            return "Failed to create a comment";
        }

    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

}
