package com.example.project.dtos;

import com.example.project.entities.User;
import com.example.project.enums.ResourceVisibilityStatus;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumRequestBody {

    private String name;
    private ResourceVisibilityStatus visibilityStatus;
}
