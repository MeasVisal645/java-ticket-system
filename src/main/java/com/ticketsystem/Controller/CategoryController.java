package com.ticketsystem.Controller;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Service.CategoryService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return categoryService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return categoryService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive
    ) {
        return categoryService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        isActive)
                .map(ApiResponse::success);
    }
}
