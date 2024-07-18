package com.example.project.entities;

import com.example.project.enums.DbStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "selectedUsersAlbums")
public class SelectedUsersAlbums {

    @Id
    @GeneratedValue
    @Column(name = "selected_user_album_id")
    private long selectedUsersAlbumId;
    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User selectedUserId;
    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name = "albumId")
    private Album albumResourceId; //??
    @Column(name = "album_resource")
    private String albumResource;
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

    public long getSelectedUsersAlbumId() {
        return selectedUsersAlbumId;
    }

    public void setSelectedUsersAlbumId(long selectedUsersAlbumId) {
        this.selectedUsersAlbumId = selectedUsersAlbumId;
    }

    public User getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(User selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public Album getAlbumResourceId() {
        return albumResourceId;
    }

    public void setAlbumResourceId(Album albumResourceId) {
        this.albumResourceId = albumResourceId;
    }

    public String getAlbumResource() {
        return albumResource;
    }

    public void setAlbumResource(String albumResource) {
        this.albumResource = albumResource;
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

    public boolean isSeeAllFiles() {
        return seeAllFiles;
    }

    public void setSeeAllFiles(boolean seeAllFiles) {
        this.seeAllFiles = seeAllFiles;
    }
}
