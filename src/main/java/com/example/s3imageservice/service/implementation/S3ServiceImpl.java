package com.example.s3imageservice.service.implementation;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.s3imageservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client s3;

    @Override
    public String getObjectUrl(String bucket, String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucket, key);
    }

    @Override
    public void putObject(String bucket, MultipartFile multipartFile, String key) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket,
                    key,
                    multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putObjectRequest);

        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error occurred while processing the file: %s", e.getMessage())
            );
        }
    }

    @Override
    public void deleteObject(String bucket, String key) {
        s3.deleteObject(bucket, key);
    }
}
