package com.ticketsystem.Controller;

import com.ticketsystem.Service.FileService;
import com.ticketsystem.Utils.ApiResponse;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
@RequestMapping("/api/v1/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ApiResponse<String>>> uploadFile(@RequestPart("file") FilePart file) {
        return fileService
                .uploadFile(file.filename(), file)
                .map(filename ->
                        ResponseEntity.status(201)
                                .body(new ApiResponse<>(
                                            HttpStatus.OK,
                                            "Uploaded Success",
                                            filename
                                        )
                                )
                );
    }

    @GetMapping("/{keyName}")
    public Mono<ResponseEntity<FileSystemResource>> getFile(@PathVariable String keyName) {
        return fileService.getFile(keyName)
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource));
    }

    @GetMapping("/download/{keyName}")
    public Mono<ResponseEntity<Resource>> downloadFile(@PathVariable String keyName) {
        return fileService.getFile(keyName)
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" +
                                        keyName +
                                        "\""
                        )
                        .body((Resource) resource)
                );
    }
}
