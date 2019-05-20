package com.gmail.kaminski.viktar.onlinemarket.repository.exception;

public class ArticleRepositoryException extends RuntimeException {
    public ArticleRepositoryException(Throwable cause) {
        super(cause);
    }

    public ArticleRepositoryException(String message) {
        super(message);
    }
}
