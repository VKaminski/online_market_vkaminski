package com.gmail.kaminski.viktar.onlinemarket.repository.exception;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(Throwable cause) {
        super(cause);
    }

    public UserRepositoryException(String message) {
        super(message);
    }
}
