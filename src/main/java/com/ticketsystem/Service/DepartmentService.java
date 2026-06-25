package com.ticketsystem.Service;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface DepartmentService {

    Flux<DepartmentDto> findAll();
    Mono<DepartmentDto> findById(Long id);
    Mono<DepartmentDto> create(DepartmentDto departmentDto);
    Mono<DepartmentDto> update(DepartmentDto departmentDto);
    Mono<Void> delete(Long id);
    Mono<PageResponse<DepartmentDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive);


}
