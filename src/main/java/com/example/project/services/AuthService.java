package com.example.project.services;
import com.example.project.dtos.LoginBody;
import com.example.project.dtos.RegisterBody;
import com.example.project.entities.User;
import com.example.project.enums.VerificationStatus;
import com.example.project.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
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

        return user;
    }

}
