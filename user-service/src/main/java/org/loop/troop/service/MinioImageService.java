package org.loop.troop.service;

import java.io.InputStream;
import java.util.UUID;

import org.loop.troop.config.MinioConfig;
import org.loop.troop.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioImageService implements ImageService {

    private MinioClient minioClient;

    private final MinioConfig minioConfig;

    @PostConstruct
    public void init() {
        try {
            this.minioClient = MinioClient.builder()
                    .endpoint(minioConfig.getEndpoint())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
            // Check if the bucket exists using BucketExistsArgs
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build())) {
                // If the bucket doesn't exist, create it using MakeBucketArgs
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to initialize Minio client", HttpStatus.INTERNAL_SERVER_ERROR,
                    "Minio Initialization Error");
        }
    }

    @Override
    public String save(String uploadLocation, MultipartFile image, String username) {

        try {

            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            InputStream imageStream = image.getInputStream();

            // Upload the file to MinIO using PutObjectArgs
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(uploadLocation + "/" + fileName)
                    .stream(imageStream, image.getSize(), -1)
                    .contentType(image.getContentType())
                    .build();
            ObjectWriteResponse response = minioClient.putObject(putObjectArgs);
            // Logging the ETag of the uploaded object
            String etag = response.etag();
            log.info("File uploaded successfully with ETag: {}", etag);

            return fileName;
        } catch (Exception e) {
            throw new ServiceException("Error uploading image to MinIO", HttpStatus.INTERNAL_SERVER_ERROR,
                    "Image Upload Error");
        }
    }

    @Override
    public String getImageUrl(String fileName) {
        try {
            // Generate a presigned URL for the file
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .method(Method.GET)
                            .build());
            log.debug("image pre-signed url : {}", url);
            return url;
        } catch (Exception e) {
            throw new ServiceException("Error generating image URL from MinIO", HttpStatus.INTERNAL_SERVER_ERROR,
                    "URL Generation Error");
        }
    }
}
