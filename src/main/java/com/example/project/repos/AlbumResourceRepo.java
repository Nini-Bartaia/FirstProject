package com.example.project.repos;

import com.example.project.entities.AlbumResources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumResourceRepo extends JpaRepository<AlbumResources, Long> {

}
