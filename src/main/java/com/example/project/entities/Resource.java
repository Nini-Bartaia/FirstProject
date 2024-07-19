package com.example.project.entities;

import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "resource")
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue
    @Column(name = "resource_id")
    private long resourceId;
    @Column(name = "resource_address")
    private String resourceAddress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User ownerId;
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type")
    private ResourceType resourceType;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private ResourceVisibilityStatus visibility;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;

    public Resource() {
        this.createTime = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;
    }

}
