package com.ticketsystem.Service;

import com.ticketsystem.Dto.OfficeDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface OfficeService {

    Flux<OfficeDto> findAll();
    Mono<OfficeDto> findById(Long id);
    Mono<OfficeDto> create(OfficeDto officeDto);
    Mono<OfficeDto> update(OfficeDto officeDto);
    Mono<Void> delete(Long id);
    Mono<PageResponse<OfficeDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive);
}
