package com.example.s3imageservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String getObjectUrl(String bucket, String key);

    void putObject(String bucket, MultipartFile multipartFile, String key);

    void deleteObject(String bucket, String key);
}
