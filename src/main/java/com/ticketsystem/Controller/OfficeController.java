package com.ticketsystem.Controller;

import com.ticketsystem.Dto.OfficeDto;
import com.ticketsystem.Service.OfficeService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/office")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return officeService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return officeService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@RequestBody OfficeDto officeDto) {
        return officeService.create(officeDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody OfficeDto officeDto) {
        return officeService.update(officeDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return officeService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive
    ) {
        return officeService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        isActive)
                .map(ApiResponse::success);
    }
}
