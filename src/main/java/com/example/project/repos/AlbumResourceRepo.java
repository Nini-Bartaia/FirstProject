package com.example.project.repos;

import com.example.project.entities.AlbumResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlbumResourceRepo extends JpaRepository<AlbumResources, Long> {
    @Query("SELECT r FROM AlbumResources r WHERE r.albumId.albumId = :albumId")
    List<AlbumResources> findByAlbumId(@Param("albumId") long albumId);

    @Query("SELECT r FROM AlbumResources r WHERE r.albumId.albumId = :albumId AND r.createDate >= :createDate")
    List<AlbumResources> findByAlbumIdAndCreateDateAfter(@Param("albumId") long albumId, @Param("createDate") LocalDateTime createDate);



}
