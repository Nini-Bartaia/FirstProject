package com.example.project.repos;

import com.example.project.entities.FriendPairs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendPairsRepo extends JpaRepository<FriendPairs, Integer> {

    @Query("SELECT f FROM FriendPairs f WHERE f.firstFriendId=:userId OR f.secondFriendId=:userId")
    List<FriendPairs> findFriendPairsByUserId(long userId);

    @Modifying
    @Transactional
    @Query("UPDATE FriendPairs f SET f.dbStatus = 'INACTIVE' WHERE (f.firstFriendId = :firstId AND f.secondFriendId = :secondId) OR (f.firstFriendId = :secondId AND f.secondFriendId = :firstId)")
    void updateStatusToInactiveByFriendIds(@Param("firstId") long firstId, @Param("secondId") long secondId);

    @Modifying
    @Transactional
    @Query("UPDATE FriendPairs f SET f.dbStatus = 'INACTIVE' WHERE f.firstFriendId = :userId OR f.secondFriendId = :userId")
    void updateStatusToInactiveByUserId(@Param("userId") long userId);

}
