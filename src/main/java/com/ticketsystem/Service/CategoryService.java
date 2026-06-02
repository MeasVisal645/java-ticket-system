package com.ticketsystem.Service;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CategoryService {

    Flux<CategoryDto> findAll();
    Mono<CategoryDto> findById(Long id);
    Mono<CategoryDto> create(CategoryDto categoryDto);
    Mono<CategoryDto> update(CategoryDto categoryDto);
    Mono<Void> delete(Long id);
    Mono<PageResponse<CategoryDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive);
}
