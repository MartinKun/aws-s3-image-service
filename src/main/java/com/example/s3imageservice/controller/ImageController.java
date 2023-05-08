package com.example.s3imageservice.controller;

import com.example.s3imageservice.entity.Image;
import com.example.s3imageservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> saveImage(
            @RequestPart(name = "file") MultipartFile file
    ) {
        imageService.saveImage(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("The image was uploaded successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(imageService.getImage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long id
    ) {
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("The image was deleted successfully.");
    }

}
