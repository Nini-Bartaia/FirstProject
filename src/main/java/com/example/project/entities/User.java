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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VerificationStatus getVerification() {
        return verification;
    }

    public void setVerification(VerificationStatus verification) {
        this.verification = verification;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    public DbStatus getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DbStatus dbStatus) {
        this.dbStatus = dbStatus;
    }

    public LocalDateTime getVerificationCodeExpiration() {
        return verificationCodeExpiration;
    }

    public void setVerificationCodeExpiration(LocalDateTime verificationCodeExpiration) {
        this.verificationCodeExpiration = verificationCodeExpiration;
    }
}
