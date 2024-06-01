package com.coding.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.concurrent.CompletableFuture;

public interface S3Service {
    CompletableFuture<PutObjectResponse> uploadFile(String path, MultipartFile file);

    CompletableFuture<DeleteObjectsResponse> cleanDirectory(String path);

    CompletableFuture<String> getPreSignedUrl(String path);

    CompletableFuture<Boolean> doesObjectExist(String path);
}
