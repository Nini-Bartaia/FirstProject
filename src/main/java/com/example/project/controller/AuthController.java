package com.example.project.controller;

import com.example.project.dtos.LoginBody;
import com.example.project.dtos.LoginResponse;
import com.example.project.dtos.RegisterBody;
import com.example.project.dtos.RegisterResponse;
import com.example.project.entities.User;
import com.example.project.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterBody registerBody) {
        this.authService.registerUser(registerBody);
        return ResponseEntity.ok("code is sent to email");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginBody loginBody, @RequestParam long id) {
        User user= authService.login(loginBody, id);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }


    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyVerificationCode(@RequestParam Integer code){

        if(!(authService.verifyUser(code)==null)){
            return ResponseEntity.ok("user is created");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("code is invalid");
        }

    }





}
