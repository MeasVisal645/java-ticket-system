package com.ticketsystem.Controller;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Service.CategoryService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Categories", description = "Category endpoints")
@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Hidden
    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return categoryService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @Hidden
    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ApiResponse::success);
    }

    @Operation(summary = "Create category", description = "Create a new category")
    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto)
                .map(ApiResponse::created);
    }

    @Operation(summary = "Update category", description = "Update an existing category")
    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto)
                .map(ApiResponse::updated);
    }

    @Operation(summary = "Delete category", description = "Delete an existing category")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return categoryService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @Operation(summary = "Get paginated categories", description = "Retrieve a paginated list of categories")
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
