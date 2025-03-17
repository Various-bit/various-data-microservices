package org.example.comment.service;

import org.example.comment.entity.Comment;

import java.util.List;

public interface CommentService {
    public String createComment(Comment comment, String username);

    public List<Comment> getAllComments();
}
