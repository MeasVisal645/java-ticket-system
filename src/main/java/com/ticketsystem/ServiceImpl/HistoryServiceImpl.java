package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.HistoryDto;
import com.ticketsystem.Dto.HistoryResponseDto;
import com.ticketsystem.Entities.Action;
import com.ticketsystem.Entities.History;
import com.ticketsystem.Entities.Priority;
import com.ticketsystem.Entities.Status;
import com.ticketsystem.Mapper.HistoryMapper;
import com.ticketsystem.Repository.HistoryRepository;
import com.ticketsystem.Service.HistoryService;
import com.ticketsystem.Utils.FilterPaginationUtils;
import com.ticketsystem.Utils.PageResponse;
import com.ticketsystem.Utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;


    @Override
    public Flux<History> findAll() {
        return historyRepository.findAll();
    }

    @Override
    public Mono<History> findById(Long id) {
        return historyRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket History Not Found")));
    }

    @Override
    public Mono<PageResponse<HistoryDto>> findPagination(Integer pageSize, Integer pageNumber, LocalDateTime startDate, LocalDateTime endDate, String search, Status status, Priority priority, Action action) {
        Criteria criteria = Criteria.empty();

        if (status != null) {
            criteria = criteria.or(Criteria.where(History.STATUS_COLUMN).is(status));
        }

        if (priority != null) {
            criteria = criteria.or(Criteria.where(History.PRIORITY_COLUMN).is(priority));
        }

        if (action != null) {
            criteria = criteria.or(Criteria.where(History.ACTION_COLUMN).is(action));
        }

        if (startDate != null && endDate != null) {
            criteria = criteria.and(History.CHANGED_AT_COLUMN).between(startDate, endDate);
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(History.TICKET_NO_COLUMN).like(pattern)
                    .or(History.SUBJECT_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                History.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.desc(History.CHANGED_AT_COLUMN)),
                HistoryMapper.INSTANCE::toDto
        );
    }

    @Override
    public Flux<HistoryResponseDto> latest(Long id) {
        return r2dbcEntityTemplate
                .select(History.class)
                .matching(
                        Query.query(Criteria.where(History.ID_COLUMN).greaterThan(id))
                                .sort(Sort.by(Sort.Direction.ASC, History.ID_COLUMN))
                )
                .all()
                .map(HistoryMapper.INSTANCE::toResponseDto);
    }
}
