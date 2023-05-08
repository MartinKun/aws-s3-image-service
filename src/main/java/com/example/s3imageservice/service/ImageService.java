package com.example.s3imageservice.service;

import com.example.s3imageservice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image getImage(Long id);

    void saveImage(MultipartFile file);

    void deleteImage(Long id);
}
