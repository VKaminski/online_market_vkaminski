package com.gmail.kaminski.viktar.onlinemarket.repository.exception;

public class ReviewRepositoryException extends RuntimeException {
    public ReviewRepositoryException(Throwable cause) {
        super(cause);
    }

    public ReviewRepositoryException(String message) {
        super(message);
    }
}
