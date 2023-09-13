package com.example.bookstore.exception;

public class EntityNotUniqueException extends RuntimeException {
    public EntityNotUniqueException(String message) {
        super(message);
    }
}
