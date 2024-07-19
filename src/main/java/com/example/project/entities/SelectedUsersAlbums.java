package com.example.project.entities;

import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "selected_users_albums")
@Getter
@Setter
public class SelectedUsersAlbums {

    @Id
    @GeneratedValue
    @Column(name = "selected_user_album_id")
    private long selectedUsersAlbumId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User selectedUserId;
    @Column(name = "album_id")
    private long albumId;
    @Column(name = "resource_id")
    private long selectedResource;
    @Column(name = "create_Date")
    private LocalDateTime createDate;
    @Column(name = "see_all_files")
    private boolean seeAllFiles;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;

    public SelectedUsersAlbums() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;

    }


}
