package com.example.project.entities;


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
    @Column(name = "resource_id")
    private Long resourceId;
    @Column(name = "user_id")
    private Long userId;


}
