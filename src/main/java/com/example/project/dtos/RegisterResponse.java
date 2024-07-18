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

//    private String name;
//    private String email;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public RegisterResponse(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
}
