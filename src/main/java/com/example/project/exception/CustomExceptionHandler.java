package com.example.project.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomExceptionHandler {

    @ExceptionHandler(AlbumDeletedException.class)
    public ResponseEntity<String> handleAlbumDeletedException(AlbumDeletedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(ResourceDeletedException.class)
    public ResponseEntity<String> ResourceDeletedExceptionHandler(ResourceDeletedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
