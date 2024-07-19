package com.example.project.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailVerificationService {

    private final EmailService emailService;


    public Integer generateVerificationCode(){

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        int hashCode = uuidString.hashCode();
        int code = Math.abs(hashCode) % 1000000;
        return code;

    }

    public void sendVerificationCode(String email, Integer code){
     //   Integer code = generateVerificationCode();
        emailService.sendEmail(email,"Verification Code","Please use the following code to complete your login: " + code );
    }

    public void sendFriendRequestToEmail(String email, String message){
        emailService.sendEmail(email,"Friend Request",message);
    }

    public LocalDateTime generateExpirationTime() {
        return LocalDateTime.now().plusMinutes(10); // Set expiration time to 10 minutes from now
    }

    public boolean verifyCode(Integer userEnteredCode, Integer sentCode, LocalDateTime expirationTime){
        if(LocalDateTime.now().isAfter(expirationTime)){
            return false;
        }

        return sentCode.equals(userEnteredCode);

       // return sentCode.equals(userEnteredCode);
    }

}
