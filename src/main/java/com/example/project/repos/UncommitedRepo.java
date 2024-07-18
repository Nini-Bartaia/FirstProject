package com.example.project.repos;

import com.example.project.entities.UncommitedFriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UncommitedRepo extends JpaRepository<UncommitedFriendRequest, Long> {


}
