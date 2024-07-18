package com.example.project.repos;

import com.example.project.entities.Album;
import com.example.project.enums.DbStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AlbumRepo extends JpaRepository<Album, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Album a SET a.dbStatus =:status WHERE a.albumId=:albumId")
    void deleteAlbum(@Param("albumId") long albumId, @Param("status") DbStatus status);


}
