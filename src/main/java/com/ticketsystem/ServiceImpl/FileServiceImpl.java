package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;
import java.nio.file.Files;

@Service
@Log4j2
public class FileServiceImpl implements FileService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String publicUrl;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    public FileServiceImpl(
            S3Client s3Client,
            @Value("${r2.bucket}") String bucketName,
            @Value("${r2.public-url}") String publicUrl
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.publicUrl = publicUrl;
    }

    @Override
    public Mono<String> uploadFile(String keyName, FilePart filePart) {
        return Mono.fromCallable(() -> Files.createTempFile("r2-", ".upload"))
                .flatMap(tempFile ->
                        filePart.transferTo(tempFile)
                                .then(Mono.fromCallable(() -> {
                                            long size = Files.size(tempFile);
                                            if (size > MAX_FILE_SIZE) {
                                                throw new RuntimeException(
                                                        "File too large. Max 5MB"
                                                );
                                            }
                                            try(InputStream input = Files.newInputStream(tempFile)) {
                                                PutObjectRequest request =
                                                        PutObjectRequest.builder()
                                                                .bucket(bucketName)
                                                                .key(keyName)
                                                                .contentType(
                                                                        filePart.headers().getContentType()!= null ? filePart.headers()
                                                                                .getContentType()
                                                                                .toString() : "application/octet-stream"
                                                                )
                                                                .contentLength(size)
                                                                .build();

                                                s3Client.putObject(request, RequestBody.fromInputStream(input, size));
                                                return publicUrl + "/" + keyName;
                                            }
                                        })
                                )
                                .doFinally(signal -> {
                                    try {
                                        Files.deleteIfExists(tempFile);
                                    }
                                    catch(Exception ignored){}
                                })

                )
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteFile(String keyName) {
        return Mono.fromRunnable(() -> {
                    DeleteObjectRequest request =
                            DeleteObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(keyName)
                                    .build();
                    s3Client.deleteObject(request);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }


    @Override
    public ResponseInputStream<GetObjectResponse> getFile(String keyName) {
        GetObjectRequest req = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        return s3Client.getObject(req);
    }
}