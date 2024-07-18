package com.example.project.repos;

import com.example.project.entities.User;
import com.example.project.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.dbStatus='INNACTIVE' WHERE u.id=:UserId")
    void deleteUserById(@Param("UserId") Long userId);

    @Transactional
    @Query("SELECT u.email, u.name, u.password FROM User u WHERE u.id = :userId AND u.dbStatus = 'Active'")
    User findUserById(@Param("userId") Long userId);

    @Transactional
    @Query("SELECT u.email, u.password FROM User u WHERE u.verification='VERIFIED' AND u.email=:email AND u.password=:password AND u.id=:id")
    Optional<User> findByEmailAndPassword( @Param("email")String email, @Param("password")String password, @Param("id")long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.verification=:status")
    void updateVerifiedStatus(@Param("status") VerificationStatus status);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.verificationCode=:enteredCode")
    User findVerificationCode(@Param("enteredCode") Integer enteredCode);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.email=:email AND u.id=:id")
    Optional<User> findByEmail(@Param("email") String email, @Param("id") long id);


}
