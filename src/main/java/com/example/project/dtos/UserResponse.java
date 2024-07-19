package com.example.project.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private long id;
    private String name;
    private String email;


    public UserResponse() {}
    public UserResponse(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
