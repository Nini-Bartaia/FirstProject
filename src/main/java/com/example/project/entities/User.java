package com.example.project.entities;

import com.example.project.dtos.RegisterBody;
import com.example.project.enums.DbStatus;
import com.example.project.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "created_Date")
    private LocalDateTime createdDate;
    @Column(name = "password")
    private String password;
    @Column(name="verified_status")
    @Enumerated(EnumType.STRING)
    private VerificationStatus verification;
    @Column(name = "verification_code")
    private Integer verificationCode;
    @Column(name = "verification_code_expiration")
    private LocalDateTime verificationCodeExpiration;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;


    public User() {
        this.createdDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;
    //    this.verification = VerificationStatus.NOTVERIFIED;
    }

}
