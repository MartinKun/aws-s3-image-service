package com.example.s3imageservice.service.implementation;

import com.example.s3imageservice.config.BucketConfig;
import com.example.s3imageservice.entity.Image;
import com.example.s3imageservice.repository.ImageRepository;
import com.example.s3imageservice.service.ImageService;
import com.example.s3imageservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final BucketConfig bucketConfig;

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Image with ID: %d not found", id)
                ));
    }

    @Override
    public void saveImage(MultipartFile file) {
        String key = UUID.randomUUID().toString();
        s3Service.putObject(
                bucketConfig.getBucket(),
                file,
                key)
        ;
        String path = String.format("https://%s.s3.amazonaws.com/%s",
                bucketConfig.getBucket(),
                key);
        Image image = new Image();
        image.setPath(path);
        imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Image with ID: %d not found", id)
                ));
        s3Service.deleteObject(
                bucketConfig.getBucket(),
                image.getPath().substring(String.format(
                        "https://%s.s3.amazonaws.com/",
                        bucketConfig.getBucket()).length()
                )
        );
        imageRepository.delete(image);
    }
}
