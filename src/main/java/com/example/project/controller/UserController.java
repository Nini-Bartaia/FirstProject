package com.example.project.controller;


import com.example.project.dtos.UserBody;
import com.example.project.dtos.UserResponse;
import com.example.project.entities.User;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {

        return userService.findAll();
    }

    @GetMapping("/getById")
    public UserResponse GetById(@RequestParam long id) {

        User user= userService.findById(id);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUserById(int id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Deleted User for user ID: " + id);
    }

    @PutMapping("/editUser")
    public UserResponse editUser(@RequestBody UserBody user){

       User modifiedUser=userService.editUser(user);
       return new UserResponse(modifiedUser.getId(), modifiedUser.getName(), modifiedUser.getEmail());
    }


}
