package com.example.project.controller;


import com.example.project.dtos.FriendRequestBody;
import com.example.project.dtos.FriendRequestResponse;
import com.example.project.entities.UncommitedFriendRequest;
import com.example.project.entities.User;
import com.example.project.services.UncommitedFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UncommitedRequestsController {

    @Autowired
    UncommitedFriendRequestService uncommitedFriendRequestService;

    @PostMapping("/sendRequest")
    public FriendRequestResponse sendRequest(@RequestBody FriendRequestBody friendRequestBody){

        UncommitedFriendRequest request= uncommitedFriendRequestService.sendRequest(friendRequestBody);

        return new FriendRequestResponse(request.getRequestId(),request.getUser().getId(), request.getLastFriendId().getId(), request.getCreateDate(), request.getRequestStatus());

    }

    @DeleteMapping("/cancelRequest")
    public FriendRequestResponse cancelRequest(@RequestBody FriendRequestBody friendRequestBody){

        UncommitedFriendRequest request= uncommitedFriendRequestService.cancelRequest(friendRequestBody);
        return new FriendRequestResponse(request.getRequestId(), request.getUser().getId(), request.getLastFriendId().getId(), request.getCreateDate(), request.getRequestStatus());
    }

    @PutMapping("/acceptRequest")
    public FriendRequestResponse acceptRequest(@RequestBody FriendRequestBody friendRequestBody){

        UncommitedFriendRequest request= uncommitedFriendRequestService.acceptRequest(friendRequestBody);
        return new FriendRequestResponse(request.getRequestId(),request.getUser().getId(), request.getLastFriendId().getId(), request.getCreateDate(), request.getRequestStatus());
    }
}
