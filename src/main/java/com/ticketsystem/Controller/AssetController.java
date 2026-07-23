package com.ticketsystem.Controller;

import com.ticketsystem.Dto.AssetDto;
import com.ticketsystem.Service.AssetService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/asset")
@AllArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return assetService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return assetService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@Valid @RequestBody AssetDto assetDto) {
        return assetService.create(assetDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@Valid @RequestBody AssetDto assetDto) {
        return assetService.update(assetDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return assetService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String filter
    ) {
        return assetService.findPagination(
                        pageSize,
                        pageNumber,
                        startDate,
                        endDate,
                        search,
                        isActive,
                        filter)
                .map(ApiResponse::success);
    }

    @GetMapping("/total")
    public Mono<ApiResponse<?>> count() {
        return assetService.count()
                .map(ApiResponse::success);
    }
}
