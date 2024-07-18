package com.example.project.exception;

public class AlbumDeletedException extends RuntimeException {

    public AlbumDeletedException(String message) {
        super(message);
    }
}
