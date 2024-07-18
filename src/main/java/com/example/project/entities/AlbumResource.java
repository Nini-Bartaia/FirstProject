package com.example.project.entities;

import com.example.project.enums.DbStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name="AlbumResources")
public class AlbumResource {

    @Id
    @GeneratedValue
    @Column(name = "album_resource_id")
    private long albumResourceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resourceId")
    private Resource resourceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumId")
    private Album albumId;
    private LocalDateTime createDate;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;

    public AlbumResource() {
        this.createDate = LocalDateTime.now(ZoneId.of("Asia/Tbilisi"));
        this.dbStatus = DbStatus.ACTIVE;
    }

    public long getAlbumResourceId() {
        return albumResourceId;
    }

    public void setAlbumResourceId(long albumResourceId) {
        this.albumResourceId = albumResourceId;
    }

    public Resource getResourceId() {
        return resourceId;
    }

    public void setResourceId(Resource resourceId) {
        this.resourceId = resourceId;
    }

    public Album getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
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
