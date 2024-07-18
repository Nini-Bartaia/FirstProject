package com.example.project.entities;

import com.example.project.enums.DbStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Entity
@Table(name = "friendPairs")
public class FriendPairs {
    @Id
    @GeneratedValue
    @Column(name = "friendPair_id")
    private long friendPairId;
    @Column(name = "first_friend_id")
    private long firstFriendId;
    @Column(name = "second_friend_id")
    private long secondFriendId;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;

    public FriendPairs() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;
    }

    public long getFriendPairId() {
        return friendPairId;
    }

    public void setFriendPairId(long friendPairId) {
        this.friendPairId = friendPairId;
    }

    public long getFirstFriendId() {
        return firstFriendId;
    }

    public void setFirstFriendId(long firstFriendId) {
        this.firstFriendId = firstFriendId;
    }

    public long getSecondFriendId() {
        return secondFriendId;
    }

    public void setSecondFriendId(long secondFriendId) {
        this.secondFriendId = secondFriendId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public DbStatus getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DbStatus dbStatus) {
        this.dbStatus = dbStatus;
    }
}

