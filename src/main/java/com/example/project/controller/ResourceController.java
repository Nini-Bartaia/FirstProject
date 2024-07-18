package com.example.project.controller;


import com.example.project.enums.ResourceVisibilityStatus;
import com.example.project.services.FileService;
import com.example.project.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

public class ResourceController {

    @Autowired
    private  FileService fileService;
    @Autowired
    private  ResourceService resourceService;

    public ResourceController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/uploadResource/{id}",consumes = "multipart/form-data")
    public void uploadResource(@PathVariable Long id, @RequestParam ResourceVisibilityStatus visibility, @RequestParam("file") MultipartFile file,@RequestParam(required = false) List<Long> selectedUserIds) {
        resourceService.uploadResource(id, file, visibility, selectedUserIds);

    }

    @GetMapping("/getResource")
    public void getUserResource(@RequestParam long userId, @RequestParam long destinationUserId, @RequestParam String file ){

        resourceService.checkStatus(destinationUserId, file);
    }
}
