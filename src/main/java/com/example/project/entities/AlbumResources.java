package com.example.project.entities;


import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "album_resources")
@Getter
@Setter
public class AlbumResources {

    @Id
    @GeneratedValue
    @Column(name = "album_resource_id")
    private long albumResourceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resourceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album albumId;
    @Column(name = "createDate")
    private LocalDateTime createDate;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus status;

    public AlbumResources() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.status = DbStatus.ACTIVE;

    }

}
