package com.example.project.entities;


import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name="uncommitedFriendRequests")
@Getter
@Setter
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


}
