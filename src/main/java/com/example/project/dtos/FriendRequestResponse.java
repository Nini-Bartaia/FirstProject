package com.example.project.dtos;

import com.example.project.entities.User;

import java.time.LocalDateTime;

public class FriendRequestResponse {

    private long requestId;
    private long firstUserId;
    private long secondUserId;
    private LocalDateTime createDate;
    private String requestStatus;

    public FriendRequestResponse(long requestId, long firstUserId, long secondUserId, LocalDateTime createDate, String requestStatus) {
        this.requestId = requestId;
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
        this.createDate = createDate;
        this.requestStatus = requestStatus;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(long firstUserId) {
        this.firstUserId = firstUserId;
    }

    public long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(long secondUserId) {
        this.secondUserId = secondUserId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
