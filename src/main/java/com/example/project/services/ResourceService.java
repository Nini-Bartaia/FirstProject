package com.example.project.services;

import com.example.project.entities.Resource;
import com.example.project.entities.SelectedUser;
import com.example.project.entities.User;
import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ResourceType;
import com.example.project.repos.FriendPairsRepo;
import com.example.project.repos.ResourceRepo;
import com.example.project.repos.SelectedUserRepo;
import com.example.project.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResourceService extends UserService {


     private final ResourceRepo resourceRepo;
     private final UserRepo userRepo;
     private final FileService fileService;
     private final FriendPairsRepo friendPairsRepo;
     private final FriendPairsService friendPairsService;
     private final SelectedUserRepo selectedUserRepo;
    private final EmailVerificationService emailVerificationService;

    @Transactional
    @CacheEvict(value = "UserService::UserService::findById", key = "#id")
    public void uploadResource(Long id, MultipartFile resource, ResourceVisibilityStatus visibilityStatus, List<Long> selectedUserIds) {

        Resource resource1 = new Resource();
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String fileName = fileService.upload(resource);
        resource1.setResourceAddress(fileName);
        resource1.setOwnerId(user);
        resource1.setResourceType(manageExtension(resource));
        resource1.setVisibility(visibilityStatus);
        manageExtension(resource);
        resourceRepo.save(resource1);

        if(visibilityStatus==ResourceVisibilityStatus.SELECTEDUSERS){
            saveSelectedUserIds(resource1.getResourceId(), selectedUserIds);

            selectedUserIds.stream().map(userId->{
                User selectedUser= userRepo.findById(userId).orElseThrow(()-> new UsernameNotFoundException("user not found"));
                emailVerificationService.sendFriendRequestToEmail(selectedUser.getEmail(), "Resource shared with you");
                return null;
            }).collect(Collectors.toList());
        }
    }



    public ResourceType manageExtension(MultipartFile file) {

        String extension = fileService.getExtension(file);


        switch (extension) {
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
            case "tif":
            case "tiff":
                return ResourceType.PHOTO;
            case "mp3":
            case "mp4":
            case "avi":
                return ResourceType.AUDIO;

            case "video":
                return  ResourceType.VIDEO;
            default:

                throw new IllegalArgumentException("Unsupported file type: " + extension);


        }

    }

    public boolean checkResourceVisibility(long userId, long resourceId){

        Resource resource = resourceRepo.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        ResourceVisibilityStatus visibilityStatus = resource.getVisibility();
        long ownerId= resource.getOwnerId().getId();

        switch (visibilityStatus) {
            case PUBLIC:
                return true;
            case PRIVATE:
                return ownerId == userId;
            case FRIENDS:
                List<Long> friendIds = friendPairsService.getFriends(ownerId);
                return friendIds.contains(userId);
            case SELECTEDUSERS:
                List<Long> selectedUserIds= getSelectedUserIdsForResource(resourceId);
                return selectedUserIds.contains(userId);
            default:
                return false;
        }
    }

    public List<Long> getSelectedUserIdsForResource(long resourceId){

       return selectedUserRepo.getSelectedUserIdsByResourceId(resourceId);
    }

    public Resource getResourceById(long resourceId) {
        return resourceRepo.findById(resourceId).orElseThrow(() -> new IllegalArgumentException("Resource not found"));
    }

    private void saveSelectedUserIds(long resourceId, List<Long> selectedUserIds){
        List<SelectedUser> selectedUsers= selectedUserIds.stream()
                .map(userId->{
            SelectedUser selectedUser = new SelectedUser();
            selectedUser.setUserId(userId);
            selectedUser.setResourceId(resourceId);
            return selectedUser;
        }).collect(Collectors.toList());
        selectedUserRepo.saveAll(selectedUsers);
    }

    public void deleteResource(long id){

        resourceRepo.deleteResource(id, DbStatus.DELETED);
    }

//    public Resource checkStatus(long destinationUserId, String file) {
//
//        return resourceRepo.checkVisibilityStatus(destinationUserId, file);
//
//
//    }


}
