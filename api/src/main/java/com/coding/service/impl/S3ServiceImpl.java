package com.coding.service.impl;

import com.coding.config.AppProperties;
import com.coding.service.S3Service;
import com.google.common.base.Preconditions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class S3ServiceImpl implements S3Service {
    private final S3AsyncClient s3AsyncClient;
    private final String bucketName;

    public S3ServiceImpl(S3AsyncClient s3AsyncClient, AppProperties appProperties) {
        this.s3AsyncClient = s3AsyncClient;
        this.bucketName = Objects.requireNonNull(Objects.requireNonNull(appProperties.s3())).bucket();
    }

    @Override
    @Async
    public CompletableFuture<PutObjectResponse> uploadFile(String path, MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            return uploadFile(data, path, file.getContentType());
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    @Async
    public CompletableFuture<DeleteObjectsResponse> cleanDirectory(String path) {
        return listObjects(path).thenCompose(this::deleteObjects);
    }

    private CompletableFuture<PutObjectResponse> uploadFile(byte[] data, String path, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .contentType(contentType)
                .build();
        AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromBytes(data);
        return s3AsyncClient.putObject(putObjectRequest, asyncRequestBody);
    }

    private CompletableFuture<List<S3Object>> listObjects(String path) {
        ListObjectsV2Request build = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(path)
                .build();
        return s3AsyncClient
                .listObjectsV2(build)
                .thenApply(ListObjectsV2Response::contents);
    }

    private CompletableFuture<DeleteObjectsResponse> deleteObjects(List<S3Object> list) {
        if (list.isEmpty()) {
            return CompletableFuture.completedFuture(DeleteObjectsResponse.builder().build());
        }
        List<ObjectIdentifier> objectIdentifiers = list.stream()
                .map(s3Object -> ObjectIdentifier.builder().key(s3Object.key()).build())
                .toList();
        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(objectIdentifiers).build())
                .build();
        return s3AsyncClient.deleteObjects(deleteObjectsRequest);
    }

    @Override
    public CompletableFuture<String> getPreSignedUrl(String path) {
        Preconditions.checkArgument(path != null, "path must not be null");
        return doesObjectExist(path)
                .thenApply(doesObjectExist -> {
                    if (!doesObjectExist)
                        return null;
                    try (S3Presigner presigner = S3Presigner.create()) {
                        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                .bucket(bucketName)
                                .key(path)
                                .build();
                        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                                .signatureDuration(Duration.ofDays(1))
                                .getObjectRequest(getObjectRequest)
                                .build();
                        PresignedGetObjectRequest presignGetObject = presigner.presignGetObject(presignRequest);
                        return presignGetObject.url().toExternalForm();
                    } catch (Exception e) {
                        return null;
                    }
                });
    }

    public CompletableFuture<Boolean> doesObjectExist(String path) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();
        return s3AsyncClient.headObject(headObjectRequest)
                .thenApply(unused -> true)
                .exceptionally(e -> false);
    }
}
