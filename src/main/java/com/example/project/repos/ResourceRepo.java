package com.example.project.repos;

import com.example.project.entities.Resource;
import com.example.project.enums.ResourceVisibilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ResourceRepo extends JpaRepository<Resource, Long> {

    @Query("SELECT re.resourceAddress FROM Resource re WHERE (re.resourceAddress=:resource AND re.visibility='PUBLIC' AND re.ownerId.id=:destinationUserId)")
    Resource checkVisibilityStatus(@Param("destinationUserId") long id, @Param("resource") String resource);


}
