package com.ticketsystem.Service;

import com.ticketsystem.Dto.AssetDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public interface AssetService {

    Flux<AssetDto> findAll();
    Mono<AssetDto> findById(Long id);
    Mono<AssetDto> create(AssetDto assetDto);
    Mono<AssetDto> update(AssetDto assetDto);
    Mono<Void> delete(Long id);
    Mono<PageResponse<AssetDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive, String assetType);

    Mono<Map<String, Long>> count();
}
