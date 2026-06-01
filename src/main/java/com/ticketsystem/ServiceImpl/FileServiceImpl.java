package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Service.FileService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
public class FileServiceImpl implements FileService {

    private final Dotenv dotenv;
    private static final Long MAX_FILE_SIZE = 1024L * 1024L;
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "application/pdf"
    );

    public FileServiceImpl(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Override
    public Mono<String> uploadFile(String keyName, FilePart filePart) {
        String uploadDir = dotenv.get("UPLOAD_DIR");
        return Mono.fromCallable(() -> {
                    Path path = Paths.get(uploadDir, keyName);
                    Files.createDirectories(path.getParent());
                    return path;
                })
                .flatMap(path -> {
                    String contentType = filePart.headers()
                            .getContentType() != null
                            ? Objects.requireNonNull(filePart.headers().getContentType()).toString()
                            : "";
                    if (!ALLOWED_TYPES.contains(contentType)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type"));
                    }
                    long contentLength = filePart.headers().getContentLength();
                    if (contentLength > 0 && contentLength > MAX_FILE_SIZE) {
                        return Mono.error(new ResponseStatusException(HttpStatus.CONTENT_TOO_LARGE, "File size exceeds 2MB"));
                    }
                    return filePart.transferTo(path)
                            .thenReturn(path.getFileName().toString());
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteFile(String keyName) {
        String uploadDir = dotenv.get("UPLOAD_DIR");
        return Mono.fromRunnable(() -> {
                    Path path = Paths.get(uploadDir, keyName);
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    @Override
    public Mono<FileSystemResource> getFile(String keyName) {
        String uploadDir = dotenv.get("UPLOAD_DIR");
        return Mono.fromCallable(() -> {
                    Path path = Paths.get(uploadDir, keyName);
                    if (!Files.exists(path)) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
                    }
                    return new FileSystemResource(path);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
