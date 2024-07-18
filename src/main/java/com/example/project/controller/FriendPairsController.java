package com.example.project.controller;

import com.example.project.services.FriendPairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendPairsController {

    @Autowired
    private FriendPairsService friendPairsService;

    @GetMapping("/get_friends")
    public List<Long> getFriendById(@RequestParam int id) {
       return friendPairsService.getFriends(id);

    }

    @DeleteMapping("/deleteFriendPair")
    public ResponseEntity<String> deleteFriendPair(@RequestParam long firstId, @RequestParam long secondId) {
        friendPairsService.deleteByUserId(firstId, secondId);
        return ResponseEntity.ok("Friend pair status updated to INACTIVE successfully");
    }

    @DeleteMapping("/deleteAllFriends")
    public ResponseEntity<String> deleteAllFriends(@RequestParam long userId) {
        friendPairsService.deleteFriends(userId);
        return ResponseEntity.ok("All friends status updated to INACTIVE successfully for user ID: " + userId);
    }

}
