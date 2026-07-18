package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.TrademarkDto;
import com.ticketsystem.Entities.Trademark;
import com.ticketsystem.Mapper.TrademarkMapper;
import com.ticketsystem.Repository.TrademarkRepository;
import com.ticketsystem.Service.TrademarkService;
import com.ticketsystem.Utils.FilterPaginationUtils;
import com.ticketsystem.Utils.PageResponse;
import com.ticketsystem.Utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrademarkServiceImpl implements TrademarkService {

    private final TrademarkRepository trademarkRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<TrademarkDto> findAll() {
        return trademarkRepository.findAll()
                .map(TrademarkMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<TrademarkDto> findById(Long id) {
        return trademarkRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trademark Not Found")))
                .map(TrademarkMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<TrademarkDto> create(TrademarkDto trademarkDto) {
        return trademarkRepository.save(Trademark.from(trademarkDto)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .build())
                .map(TrademarkMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<TrademarkDto> update(TrademarkDto trademarkDto) {
        return trademarkRepository.findById(trademarkDto.getId())
                .flatMap(existing -> {
                    TrademarkDto.update(existing, trademarkDto);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return trademarkRepository.save(existing);
                })
                .map(TrademarkMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return trademarkRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trademark Not Found")))
                .flatMap(trademarkRepository::delete);
    }

    @Override
    public Mono<PageResponse<TrademarkDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive) {
        Criteria criteria = Criteria.empty();

        if (isActive != null) {
            criteria = criteria.or(Criteria.where(Trademark.IS_ACTIVE_COLUMN).is(isActive));
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(Trademark.NAME_COLUMN).like(pattern).or(Trademark.FULLNAME_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Trademark.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.asc(Trademark.ID_COLUMN)),
                TrademarkMapper.INSTANCE::toDto
        );
    }
}
