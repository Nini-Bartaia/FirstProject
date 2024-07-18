//package com.example.project.repos;
//
//import com.example.project.entities.User;
//import com.example.project.enums.VerificationStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//public interface AuthRepo extends JpaRepository<User, Long> {
//
//    Optional<User> findByEmailAndPassword(String email, String password);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE User u SET u.verification=:status")
//    void updateVerifiedStatus(@Param("status") VerificationStatus status);
//
//    @Transactional
//    @Query("SELECT u FROM User u WHERE u.verificationCode=:enteredCode")
//    User findVerificationCode(@Param("enteredCode") Integer enteredCode);
//
//
//}
