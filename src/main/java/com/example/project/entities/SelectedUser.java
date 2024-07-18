package com.example.project.entities;


import com.example.project.enums.DbStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "selectedUser")
public class SelectedUser {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    private DbStatus dbStatus;


}
