package com.example.project.services;

import com.example.project.dtos.FriendRequestBody;
import com.example.project.entities.FriendPairs;
import com.example.project.entities.UncommitedFriendRequest;
import com.example.project.entities.User;
import com.example.project.enums.DbStatus;
import com.example.project.repos.FriendPairsRepo;
import com.example.project.repos.UncommitedRepo;
import com.example.project.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UncommitedFriendRequestService {


    private  final UserRepo userRepo;
  //  private final AuthRepo userRepo;
    private final UncommitedRepo uncommitedRepo;
    private final FriendPairsRepo friendPairsRepo;
    private final EmailVerificationService emailVerificationService;


    public UncommitedFriendRequest sendRequest(FriendRequestBody friendRequestBody){

       User user= userRepo.findById(friendRequestBody.getFirstUserId()).orElseThrow(()-> new RuntimeException("User not found"));
       User seocondUser= userRepo.findById(friendRequestBody.getLastUserId()).orElseThrow(()-> new RuntimeException("User not found"));
       UncommitedFriendRequest uncommitedFriendRequest = new UncommitedFriendRequest();
       uncommitedFriendRequest.setUser(user);
       uncommitedFriendRequest.setLastFriendId(seocondUser);
       uncommitedFriendRequest.setRequestStatus("UNCOMMITED");

       emailVerificationService.sendFriendRequestToEmail(user.getEmail(), "You have new friend request");
       return uncommitedRepo.save(uncommitedFriendRequest);
    }


    public UncommitedFriendRequest cancelRequest(FriendRequestBody friendRequestBody){
        User user= userRepo.findById(friendRequestBody.getFirstUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        User seocondUser= userRepo.findById(friendRequestBody.getLastUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        UncommitedFriendRequest uncommitedFriendRequest1 = new UncommitedFriendRequest();

        uncommitedFriendRequest1.setUser(user);
        uncommitedFriendRequest1.setLastFriendId(seocondUser);
        uncommitedFriendRequest1.setRequestStatus("CANCELLED");
        return uncommitedRepo.save(uncommitedFriendRequest1);
    }

    public UncommitedFriendRequest acceptRequest(FriendRequestBody friendRequestBody){
        User user= userRepo.findById(friendRequestBody.getFirstUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        User secondUser= userRepo.findById(friendRequestBody.getLastUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        UncommitedFriendRequest uncommitedFriendRequest1 = new UncommitedFriendRequest();
        uncommitedFriendRequest1.setUser(user);
        uncommitedFriendRequest1.setLastFriendId(secondUser);
        uncommitedFriendRequest1.setRequestStatus("COMMITED");

        FriendPairs friendPairs = new FriendPairs();
        friendPairs.setFirstFriendId(user.getId());
        friendPairs.setSecondFriendId(secondUser.getId());
        friendPairs.setCreateDate(uncommitedFriendRequest1.getCreateDate());
        friendPairs.setDbStatus(DbStatus.ACTIVE);
        friendPairsRepo.save(friendPairs);
        return uncommitedRepo.save(uncommitedFriendRequest1);
    }

}
