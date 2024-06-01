package com.coding.web.api;

import com.coding.config.AppProperties;
import com.coding.service.S3Service;
import com.coding.web.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/s3")
public class S3Resource {
    private final S3Service service;
    private static final Logger log = LoggerFactory.getLogger(S3Resource.class);
    private final AppProperties appProperties;
    private final String bucket;
    private final S3AsyncClient s3AsyncClient;

    public S3Resource(S3Service service, AppProperties appProperties, S3AsyncClient s3AsyncClient) {
        this.service = service;
        this.appProperties = appProperties;
        this.bucket = Objects.requireNonNull(appProperties.s3()).bucket();
        this.s3AsyncClient = s3AsyncClient;
    }

    @PostMapping("upload")
    public DeferredResult<ResponseEntity<ApiResponse>> uploadFile(MultipartFile file) {
        DeferredResult<ResponseEntity<ApiResponse>> deferredResult = new DeferredResult<>();
        service.uploadFile("test", file)
                .thenAccept(response -> {
                    log.info("Upload success");
                    var success = ResponseEntity.ok(ApiResponse.builder().status(ApiResponse.Status.TRUE).build());
                    deferredResult.setResult(success);
                })
                .exceptionally(e -> {
                    log.info("Upload failed");
                    var error = ResponseEntity
                            .internalServerError()
                            .body(ApiResponse.builder()
                                    .status(ApiResponse.Status.FALSE)
                                    .build());
                    deferredResult.setResult(error);
                    return null;
                });
        return deferredResult;
    }

    @GetMapping("/url")
    public ResponseEntity<ApiResponse> getPreSignedUrl() {
        try (S3Presigner presigner = S3Presigner.create()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(Objects.requireNonNull(bucket))
                    .key("test")
                    .build();
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofDays(1))
                    .getObjectRequest(getObjectRequest)
                    .build();
            //s3AsyncClient.getObject(getObjectRequest).join();
            PresignedGetObjectRequest getObject = presigner.presignGetObject(presignRequest);
            String url = getObject.url().toString();
            String v = getObject.url().toExternalForm();
            return ResponseEntity.ok(ApiResponse.builder().data(Map.of("url", url)).build());
        }
    }
}
