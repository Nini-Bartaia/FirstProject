package com.example.project.controller;

import com.example.project.exception.ExceptionBody;
import com.example.project.exception.ResourceUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class exceptionController {

    @ExceptionHandler(ResourceUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleSourceUpload(ResourceUploadException e){
        return new ExceptionBody(e.getMessage());
    }

}
