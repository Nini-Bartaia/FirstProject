package com.example.project.services;

import com.example.project.entities.Resource;
import com.example.project.entities.User;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ResourceType;
import com.example.project.repos.FriendPairsRepo;
import com.example.project.repos.ResourceRepo;
import com.example.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@NoArgsConstructor
public class ResourceService extends UserService {


    @Autowired
    private ResourceRepo resourceRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FileService fileService;
    @Autowired
    private FriendPairsRepo friendPairsRepo;
    @Autowired
    private FriendPairsService friendPairsService;

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

        ///?? unda davamato logika
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

    public Resource checkStatus(long destinationUserId, String file) {

        return resourceRepo.checkVisibilityStatus(destinationUserId, file);


    }


}
