package com.example.project.entities;

import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ResourceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "resource")
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

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceAddress() {
        return resourceAddress;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public ResourceVisibilityStatus getVisibility() {
        return visibility;
    }

    public void setVisibility(ResourceVisibilityStatus visibility) {
        this.visibility = visibility;
    }

    public DbStatus getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DbStatus dbStatus) {
        this.dbStatus = dbStatus;
    }
}
