package com.example.project.repos;

import com.example.project.entities.Album;
import com.example.project.entities.Resource;
import com.example.project.entities.SelectedUsersAlbums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SelectedUserAlbumRepo extends JpaRepository<SelectedUsersAlbums, Long> {

    @Query("SELECT s.selectedUserId.id FROM SelectedUsersAlbums s WHERE s.albumId=:albumId")
    List<Long> getSelectedUserIdsByResourceId(@Param("albumId") long albumId);


    @Query("SELECT s FROM SelectedUsersAlbums s WHERE s.albumId=:albumId AND s.selectedUserId.id=:userId")
    Optional<SelectedUsersAlbums> findByAlbumIdAndUserId(@Param("albumId") long albumId, @Param("userId") long userId);


    @Query("SELECT s.selectedUserId.id FROM SelectedUsersAlbums s WHERE s.albumId=:albumId")
    List<Long> selectedIds(@Param("albumId") long albumId);

}
