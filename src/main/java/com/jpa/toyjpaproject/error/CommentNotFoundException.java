package com.jpa.toyjpaproject.error;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(long id) {
        super("Comment not found: " + id);
    }
}
