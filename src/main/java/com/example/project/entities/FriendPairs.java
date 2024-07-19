package com.example.project.entities;

import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Entity
@Table(name = "friendPairs")
@Getter
@Setter
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

}

