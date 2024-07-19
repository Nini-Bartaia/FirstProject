package com.example.project.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    Integer code;


    public RegisterResponse(Integer code) {
        this.code = code;
    }

}
