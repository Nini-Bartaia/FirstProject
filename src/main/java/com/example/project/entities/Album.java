package com.example.project.entities;

import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceType;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ShowFiles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "Albums")
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "album_id")
    private long albumId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User ownerId;
    @Column(name = "name")
    private String name;
    @Column(name = "album_type")
    @Enumerated(EnumType.STRING)
    private ResourceType albumType;
    @Column(name = "visibility")
    @Enumerated(EnumType.STRING)
    private ResourceVisibilityStatus visibility;
    @Column(name = "create_Date")
    private LocalDateTime createDate;
    @Column(name = "show_files")
    @Enumerated(EnumType.STRING)
    private ShowFiles show;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;


    public Album() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;
    }


    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", ownerId=" + ownerId.getId() +
                ", name='" + name + '\'' +
                ", albumType='" + albumType + '\'' +
                ", visibility=" + visibility +
                ", createDate=" + createDate +
                ", dbStatus=" + dbStatus +
                '}';
    }


}
