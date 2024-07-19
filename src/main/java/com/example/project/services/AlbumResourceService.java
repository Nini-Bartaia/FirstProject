package com.example.project.services;

import com.example.project.entities.Album;
import com.example.project.entities.AlbumResources;
import com.example.project.entities.Resource;
import com.example.project.entities.SelectedUsersAlbums;
import com.example.project.enums.DbStatus;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.enums.ShowFiles;
import com.example.project.exception.AlbumDeletedException;
import com.example.project.exception.AlbumTypeDontMatchError;
import com.example.project.exception.NoAccessError;
import com.example.project.exception.ResourceDeletedException;
import com.example.project.props.MinioProperties;
import com.example.project.repos.*;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumResourceService {

    private final AlbumRepo albumRepo;
    private final ResourceRepo resourceRepo;
    private final AlbumResourceRepo albumResourceRepo;
    private final SelectedUserAlbumRepo selectedUserAlbumRepo;
    private final FriendPairsService friendPairsService;
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;



    public AlbumResources addResourceToAlbum(long albumId, long resourceId){

        Album album = albumRepo.findById(albumId).orElseThrow(()->new RuntimeException("Album not found"));

        if(album.getDbStatus()== DbStatus.DELETED){
            throw new AlbumDeletedException("Album not found");
        }
        Resource resource= resourceRepo.findById(resourceId).orElseThrow(()->new RuntimeException("Resource not found"));
        if(resource.getDbStatus()== DbStatus.DELETED){
            throw new ResourceDeletedException("Resource not found");
        }

        if(!checkResourceType(albumId, resourceId)){

            throw new AlbumTypeDontMatchError(" Album type dont match to resource type");
        }
        AlbumResources albumResources = new AlbumResources();
        albumResources.setAlbumId(album);
        albumResources.setResourceId(resource);
        albumResources.setResourceId(resource);
        albumResourceRepo.save(albumResources);

        Album album1= new Album();
        return albumResources;

    }

    public boolean checkResourceType(long albumId, long resourceId){

        Album album= albumRepo.findById(albumId).orElseThrow(()->new RuntimeException("Album not found"));
        Resource resource= resourceRepo.findById(resourceId).orElseThrow(()->new RuntimeException("Resource not found"));
        if(album.getAlbumType() == resource.getResourceType()){
            return true;

        }else{
            return false;
        }

    }

    public List<String> getResourcesForUserInAlbum(long userId, long albumId) {
        Album album = albumRepo.findById(albumId).orElseThrow(() -> new RuntimeException("Album not found"));
        List<Long> userIds = friendPairsService.getFriends(userId);
        List<Long> selectedIds = selectedUserAlbumRepo.selectedIds(albumId);

        if (album.getDbStatus() == DbStatus.DELETED) {
            throw new AlbumDeletedException("Album not found");
        }

        if (album.getVisibility() == ResourceVisibilityStatus.PRIVATE && album.getOwnerId().getId() != userId) {
            throw new NoAccessError("No Access to that Album");
        } else if (album.getVisibility() == ResourceVisibilityStatus.FRIENDS && !userIds.contains(userId)) {
            throw new NoAccessError("No Access to that Album");
        } else if (selectedIds.contains(userId)) {
            throw new NoAccessError("No Access to that Album");
        }

        List<AlbumResources> albumResources;

        if (album.getShow() == ShowFiles.ALL) {
            albumResources = albumResourceRepo.findByAlbumId(albumId);
        } else if (album.getShow() == ShowFiles.AFTERINSERT) {
            SelectedUsersAlbums userAlbum = selectedUserAlbumRepo.findByAlbumIdAndUserId(albumId, userId)
                    .orElseThrow(() -> new RuntimeException("User not found in album"));
            LocalDateTime userAddedDate = userAlbum.getCreateDate();
            albumResources = albumResourceRepo.findByAlbumIdAndCreateDateAfter(albumId, userAddedDate);
        } else {
            throw new RuntimeException("Invalid show files option");
        }

        return albumResources.stream()
                .map(albumResource -> getSignedUrl(albumResource.getResourceId().getResourceAddress()))
                .collect(Collectors.toList());
    }


    private String getSignedUrl(String resourcePath) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioProperties.getBucket())
                    .object(resourcePath)
                    .expiry(7, TimeUnit.DAYS)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error generating signed URL", e);
        }
    }

}
