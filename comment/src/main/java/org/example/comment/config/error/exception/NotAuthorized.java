package org.example.comment.config.error.exception;

public class NotAuthorized extends RuntimeException {
    public NotAuthorized(String message) {
        super(message);
    }
}
