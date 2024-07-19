package com.example.project.exception;

public class NoAccessError extends RuntimeException{
    public NoAccessError(String message) {
        super(message);
    }
}
