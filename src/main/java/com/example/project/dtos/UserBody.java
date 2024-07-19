package com.example.project.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UserBody {
    private long id;
    private String name;
    private String email;
    private String password;

    public UserBody(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
