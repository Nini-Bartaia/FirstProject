package com.example.project.controller;

import com.example.project.dtos.AlbumRequestBody;
import com.example.project.entities.Album;
import com.example.project.entities.Resource;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/create_album")
    public ResponseEntity<Long> createAlbum(@RequestParam Long userId, @RequestParam String albumName, @RequestParam  ResourceVisibilityStatus visibilityStatus, @RequestParam(required = false) List<Long> selectedUserIds) {

        try {
            Album album= albumService.createAlbum(userId, albumName,visibilityStatus, selectedUserIds);
            long albumId= album.getAlbumId();

            return ResponseEntity.ok(albumId);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/get_album")
    public ResponseEntity<String> getAlbum(@RequestParam long userId, @RequestParam long albumId) {

        if(albumService.checkVisibilityStatus(userId, albumId)) {

            Album album= albumService.getAlbumById(albumId);
            return new ResponseEntity<>("Album found" + album, HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Album not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete_mapping")
    public ResponseEntity<String> deleteMapping(@RequestParam long userId, @RequestParam long albumId) {
     try {
         albumService.deleteAlbum(albumId);
         return ResponseEntity.status(HttpStatus.OK).build();

     }catch (Exception e) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     }

    }




}
