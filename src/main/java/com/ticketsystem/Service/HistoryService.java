package com.ticketsystem.Service;

import com.ticketsystem.Dto.HistoryDto;
import com.ticketsystem.Dto.HistoryResponseDto;
import com.ticketsystem.Dto.LatestHistoryResponse;
import com.ticketsystem.Entities.Action;
import com.ticketsystem.Entities.History;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public interface HistoryService {

    Flux<History> findAll();
    Mono<History> findById(Long id);
    Mono<PageResponse<HistoryDto>> findPagination(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate, String search, Status status, Priority priority, Action action);

    // Webhook
    Mono<LatestHistoryResponse> latest(Long id);
    Mono<Long> maxId();
}
