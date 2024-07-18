package com.example.project.controller;

import com.example.project.services.AlbumResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlbumResourceController {

    private final AlbumResourceService albumResourceService;

    @PostMapping("add_resource_to_album")
    public ResponseEntity<String> addResourceToAlbum(@RequestParam long albumId, @RequestParam long resourceId) {

        try {
            albumResourceService.addResourceToAlbum(albumId, resourceId);
            return  ResponseEntity.ok("Resource successfully added to album");
        }catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }



}
