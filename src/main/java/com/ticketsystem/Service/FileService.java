package com.ticketsystem.Service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public interface FileService {

    Mono<String> uploadFile(String keyName, FilePart file);
    Mono<Void> deleteFile(String keyName);

    ResponseInputStream<GetObjectResponse> getFile(String keyName);
}
