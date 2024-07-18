package com.example.project.services;

import com.example.project.entities.Album;
import com.example.project.entities.AlbumResources;
import com.example.project.entities.Resource;
import com.example.project.enums.DbStatus;
import com.example.project.exception.AlbumDeletedException;
import com.example.project.exception.ResourceDeletedException;
import com.example.project.repos.AlbumRepo;
import com.example.project.repos.AlbumResourceRepo;
import com.example.project.repos.ResourceRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlbumResourceService {

    private final AlbumRepo albumRepo;
    private final ResourceRepo resourceRepo;
    private final AlbumResourceRepo albumResourceRepo;

    public AlbumResources addResourceToAlbum(long albumId, long resourceId){

        Album album = albumRepo.findById(albumId).orElseThrow(()->new RuntimeException("Album not found"));

        if(album.getDbStatus()== DbStatus.DELETED){
            throw new AlbumDeletedException("Album not found");
        }
        Resource resource= resourceRepo.findById(resourceId).orElseThrow(()->new RuntimeException("Resource not found"));
        if(resource.getDbStatus()== DbStatus.DELETED){
            throw new ResourceDeletedException("Resource not found");
        }
        AlbumResources albumResources = new AlbumResources();
        albumResources.setAlbumId(album);
        albumResources.setResourceId(resource);
        albumResourceRepo.save(albumResources);

        Album album1= new Album();
        return albumResources;

    }
}
