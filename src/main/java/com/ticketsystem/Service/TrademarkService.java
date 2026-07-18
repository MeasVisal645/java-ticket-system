package com.ticketsystem.Service;

import com.ticketsystem.Dto.TrademarkDto;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface TrademarkService {

    Flux<TrademarkDto> findAll();
    Mono<TrademarkDto> findById(Long id);
    Mono<TrademarkDto> create(TrademarkDto trademarkDto);
    Mono<TrademarkDto> update(TrademarkDto trademarkDto);
    Mono<Void> delete(Long id);
    Mono<PageResponse<TrademarkDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive);
}
