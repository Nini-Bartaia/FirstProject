package com.example.project.entities;


import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name="uncommitedFriendRequests")
public class UncommitedFriendRequest {

    @Id
    @GeneratedValue
    @Column(name = "request_id")
    private long requestId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondUserId")
    private User lastFriendId;
    @Column(name = "create_Date")
    private LocalDateTime createDate;
    @Column(name = "request_status")
    private String requestStatus;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;


    public UncommitedFriendRequest() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;

    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getLastFriendId() {
        return lastFriendId;
    }

    public void setLastFriendId(User lastFriendId) {
        this.lastFriendId = lastFriendId;
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

    public DbStatus getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DbStatus dbStatus) {
        this.dbStatus = dbStatus;
    }
}
