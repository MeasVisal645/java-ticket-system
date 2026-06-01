package com.ticketsystem.Service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface FileService {

    Mono<String> uploadFile(String keyName, FilePart file);
    Mono<Void> deleteFile(String keyName);
    Mono<FileSystemResource> getFile(String keyName);
}
