package com.example.project.services;

import com.example.project.entities.FriendPairs;
import com.example.project.repos.FriendPairsRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class FriendPairsService {

    @Autowired
    private FriendPairsRepo friendPairsRepo;
    public List<Long> getFriends(long userId) {
        List<FriendPairs> friendPairs = friendPairsRepo.findFriendPairsByUserId(userId);

        return friendPairs.stream()
                .map(pair -> {
                    if (pair.getFirstFriendId() == userId) {
                        return pair.getSecondFriendId();
                    } else {
                        return pair.getFirstFriendId();
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "FriendPairsService::getFriends", key = "#userId")
    public void deleteFriends(long userId) {
        friendPairsRepo.updateStatusToInactiveByUserId(userId);
    }

    @Transactional
    @CacheEvict(value = "FriendPairsService::getFriends", key = "#userId+'.'+ #secondId" )
    public void deleteByUserId(long firstId, long secondId) {
        friendPairsRepo.updateStatusToInactiveByFriendIds(firstId, secondId);
    }

}
