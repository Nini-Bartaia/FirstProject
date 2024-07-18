package com.example.project.services;


import com.example.project.dtos.UserBody;
import com.example.project.dtos.UserResponse;
import com.example.project.entities.User;
import com.example.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<UserResponse> findAll() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    @Transactional(readOnly = true)
    @Cacheable(value="UserService::findById", key="#id")
    public User findById(long id) {
        Optional<User> optionalUser= userRepo.findById(id);
        return optionalUser.orElseThrow(()-> new RuntimeException("User Not Found"));
    }

    @Transactional
    @CacheEvict(value="UserService::findById", key="#id")
    public void deleteById(long id) {
        userRepo.deleteUserById(id);
    }

    @Transactional
    @Caching(put={@CachePut(value = "UserService::findById", key = "#modifiedUser.id")})
    public User editUser(UserBody modifiedUser){

        User user=userRepo.findById(modifiedUser.getId()).orElseThrow(()-> new RuntimeException("User Not Found"));
        user.setName(modifiedUser.getName());
        user.setEmail(modifiedUser.getEmail());
        user.setPassword(modifiedUser.getPassword());
        userRepo.save(user);
        return user;



    }



}
