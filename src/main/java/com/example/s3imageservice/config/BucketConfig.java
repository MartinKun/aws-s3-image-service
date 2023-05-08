package com.example.s3imageservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    private String bucket;

    public BucketConfig(@Value("${s3.bucket}") String bucket) {
        this.bucket = bucket;
    }

    @Bean
    public String getBucket(){
        return this.bucket;
    }
}
