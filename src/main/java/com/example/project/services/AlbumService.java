package com.example.project.services;


import com.example.project.entities.*;
import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceType;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ShowFiles;
import com.example.project.repos.AlbumRepo;
import com.example.project.repos.FriendPairsRepo;
import com.example.project.repos.SelectedUserAlbumRepo;
import com.example.project.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepo albumRepo;
    private final UserRepo userRepo;
    private final FriendPairsRepo friendPairsRepo;
    private final FriendPairsService friendPairsService;
    private final SelectedUserAlbumRepo selectedUserAlbumRepo;
    private final EmailVerificationService emailVerificationService;

    public Album createAlbum(long userId, String albumName, ResourceVisibilityStatus visibilityStatus, ResourceType resourceType, List<Long> selectedUserIds, ShowFiles show) {

        User user=userRepo.findById(userId).orElseThrow(()-> new RuntimeException("use not found"));
        Album album = new Album();
        album.setName(albumName);
        album.setAlbumType(resourceType);
        album.setShow(show);
        album.setOwnerId(user);
        album.setVisibility(visibilityStatus);
        albumRepo.save(album);

        if(visibilityStatus==ResourceVisibilityStatus.SELECTEDUSERS){

            saveSelectedUserIds(album.getAlbumId(), selectedUserIds);
            selectedUserIds.stream().map(id->{
                User selectedUser= userRepo.findById(id).orElseThrow(()-> new UsernameNotFoundException("user not found"));
                emailVerificationService.sendFriendRequestToEmail(selectedUser.getEmail(), "Album shared with you");
                return null;
            }).collect(Collectors.toList());

        }

        return album;
    }

    public boolean checkVisibilityStatus(long userId, long albumId){

        Album album = albumRepo.findById(albumId).orElseThrow(()-> new RuntimeException("album not found"));
        ResourceVisibilityStatus visibilityStatus = album.getVisibility();
        long ownerId= album.getOwnerId().getId();
         switch(visibilityStatus){
            case PUBLIC:
                return true;
            case PRIVATE:
                return ownerId == userId;
            case FRIENDS:
               List<Long> friends= friendPairsService.getFriends(ownerId);
               return friends.contains(userId);

             case SELECTEDUSERS:
                 List<Long> selectedUserIds= getSelectedUserIdsForAlbum(albumId);
                 return selectedUserIds.contains(userId);

             default:
                 return false;


        }

    }

    public List<Long> getSelectedUserIdsForAlbum(long albumId){

        return selectedUserAlbumRepo.getSelectedUserIdsByResourceId(albumId);
    }

    public Album getAlbumById(long albumId){
        return albumRepo.findById(albumId).orElseThrow(()-> new RuntimeException("album not found"));
    }

    private void saveSelectedUserIds(long albumId, List<Long> selectedUserIds){
        List<SelectedUsersAlbums> selectedUsers= selectedUserIds.stream()
                .map(userId->{
                    User user= userRepo.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
                    SelectedUsersAlbums selectedUsersAlbums= new SelectedUsersAlbums();
                    selectedUsersAlbums.setSelectedUserId(user);
                    selectedUsersAlbums.setAlbumId(albumId);
                     return selectedUsersAlbums;

                }).collect(Collectors.toList());
        selectedUserAlbumRepo.saveAll(selectedUsers);
    }


    public void deleteAlbum(long id){

        albumRepo.deleteAlbum(id, DbStatus.DELETED);
    }


}
