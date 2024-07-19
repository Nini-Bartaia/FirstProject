package com.example.project.exception;

public class AlbumTypeDontMatchError extends RuntimeException {

    public AlbumTypeDontMatchError(String message) {
        super(message);
    }
}
