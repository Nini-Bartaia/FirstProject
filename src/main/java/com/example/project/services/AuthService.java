package com.example.project.services;


import com.example.project.dtos.LoginBody;
import com.example.project.dtos.RegisterBody;
import com.example.project.dtos.RegisterResponse;
import com.example.project.entities.User;
import com.example.project.enums.VerificationStatus;
//import com.example.project.repos.AuthRepo;
import com.example.project.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    //private final AuthRepo authRepository;
    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    //private boolean verified=false;

//    @Transactional
//    @Caching(cacheable = {@Cacheable(value = "userService::findById", key = "#registerBody.")})
    public void registerUser(RegisterBody registerBody) {
        User user = new User();
        user.setName(registerBody.getName());
        user.setEmail(registerBody.getEmail());
        user.setPassword(passwordEncoder.encode(registerBody.getPassword()));
        user.setVerification(VerificationStatus.NOTVERIFIED);


        Integer code= emailVerificationService.generateVerificationCode();
        emailVerificationService.sendVerificationCode(registerBody.getEmail(), code);
        user.setVerificationCodeExpiration(emailVerificationService.generateExpirationTime());

        user.setVerificationCode(code);
        userRepo.save(user);
        //return new RegisterResponse(code);

    }
    public User verifyUser(Integer enteredCode){

        System.out.println(enteredCode);

        User user= userRepo.findVerificationCode(enteredCode);


        if(user ==null){
            throw new RuntimeException("Invalid verification code");

        }
        user.setVerification(VerificationStatus.VERIFIED);
        userRepo.save(user);
        return user;


       // boolean isVerified = emailVerificationService.verifyCode(enteredCode, authRepository.findVerificationCode());
//        if(isVerified){
//            user.setVerification(VerificationStatus.VERIFIED);
//            authRepository.save(user);
//        }
//        return isVerified;
    }


    public User login(LoginBody loginBody, long id){

        User user = userRepo.findByEmail(loginBody.getEmail(),id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(loginBody.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        if(!user.getVerification().equals(VerificationStatus.VERIFIED)){
            throw new RuntimeException("User not verified");
        }

//        return userRepo.findByEmailAndPassword(loginBody.getEmail(), passwordEncoder.encode(loginBody.getPassword(), user.getPassword()), id)
//                .orElseThrow(()-> new RuntimeException("User not found"));

        return user;
    }

}
