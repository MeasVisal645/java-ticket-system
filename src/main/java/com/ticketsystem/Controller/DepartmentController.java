package com.ticketsystem.Controller;

import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Service.DepartmentService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return departmentService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@RequestBody DepartmentDto departmentDto) {
        return departmentService.create(departmentDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody DepartmentDto departmentDto) {
        return departmentService.update(departmentDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return departmentService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active
    ) {
        return departmentService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        active)
                .map(ApiResponse::success);
    }

}
