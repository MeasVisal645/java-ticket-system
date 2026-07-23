package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.OfficeDto;
import com.ticketsystem.Entities.Office;
import com.ticketsystem.Mapper.OfficeMapper;
import com.ticketsystem.Repository.OfficeRepository;
import com.ticketsystem.Service.OfficeService;
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
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<OfficeDto> findAll() {
        return officeRepository.findAll()
                .map(OfficeMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<OfficeDto> findById(Long id) {
        return officeRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found")))
                .map(OfficeMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<OfficeDto> create(OfficeDto officeDto) {
        return officeRepository.save(Office.from(officeDto)
                        .active(true)
                        .createdAt(LocalDateTime.now())
                .build())
                .map(OfficeMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<OfficeDto> update(OfficeDto officeDto) {
        return officeRepository.findById(officeDto.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found")))
                .flatMap(existing -> {
                    OfficeDto.update(existing, officeDto);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return officeRepository.save(existing);
                })
                .map(OfficeMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return officeRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Office not found")))
                .flatMap(officeRepository::delete);
    }

    @Override
    public Mono<PageResponse<OfficeDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive) {
        Criteria criteria = Criteria.empty();

        if (isActive != null) {
            criteria = criteria.or(Criteria.where(Office.IS_ACTIVE_COLUMN).is(isActive));
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(Office.NAME_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Office.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.asc(Office.ID_COLUMN)),
                OfficeMapper.INSTANCE::toDto
        );
    }
}
