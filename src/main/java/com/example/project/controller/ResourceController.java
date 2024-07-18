package com.example.project.controller;


import com.example.project.entities.Resource;
import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.services.FileService;
import com.example.project.services.ResourceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final FileService fileService;
    private final ResourceService resourceService;


    @PostMapping(value = "/uploadResource/{id}",consumes = "multipart/form-data")
    public ResponseEntity<String> uploadResource(@PathVariable Long id, @RequestParam ResourceVisibilityStatus visibility, @RequestParam("file") MultipartFile file,@RequestParam(required = false) List<Long> selectedUserIds) {

        try {

            resourceService.uploadResource(id, file, visibility, selectedUserIds);
            return new ResponseEntity<>("Resource uploaded successfully", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Failed to upload resource"+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/getResource")
    public ResponseEntity<String> getUserResource(@RequestParam long userId , @RequestParam long resourceId ){

        if(resourceService.checkResourceVisibility(userId, resourceId)){

            Resource resource = resourceService.getResourceById(resourceId);
            return new ResponseEntity<>("Resource found", HttpStatus.OK);
        } else{

            return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteResource/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable Long id){

        try {
            resourceService.deleteResource(id);
            return new ResponseEntity<>("Resource deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to delete resource"+ e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }
}
