package com.example.project.repos;

import com.example.project.entities.SelectedUser;
import com.example.project.enums.DbStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SelectedUserRepo extends JpaRepository<SelectedUser, Long> {

    @Query("SELECT s.userId FROM SelectedUser s WHERE s.resourceId=:resourceId")
    List<Long> getSelectedUserIdsByResourceId(@Param("resourceId") long resourceId);


}
