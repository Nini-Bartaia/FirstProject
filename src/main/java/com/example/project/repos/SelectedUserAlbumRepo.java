package com.example.project.repos;

import com.example.project.entities.Album;
import com.example.project.entities.SelectedUsersAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectedUserAlbumRepo extends JpaRepository<SelectedUsersAlbums, Long> {

    @Query("SELECT s.selectedUserId.id FROM SelectedUsersAlbums s WHERE s.albumId=:albumId")
    List<Long> getSelectedUserIdsByResourceId(@Param("albumId") long albumId);
//
//    @Query("SELECT s.albumId FROM SelectedUsersAlbums s WHERE s.albumId=:albumId AND s.use")
//    List<Album> getAlbumForSelectedUser(@Param("userId") long userId, @Param("albumId") long albumId);
}
