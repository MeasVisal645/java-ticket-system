package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.AssetDto;
import com.ticketsystem.Entities.Asset;
import com.ticketsystem.Entities.Ticket;
import com.ticketsystem.Mapper.AssetMapper;
import com.ticketsystem.Repository.AssetRepository;
import com.ticketsystem.Service.AssetService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<AssetDto> findAll() {
        return assetRepository.findAll()
                .map(AssetMapper.INSTANCE::toAssetDTO);
    }

    @Override
    public Mono<AssetDto> findById(Long id) {
        return assetRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trademark Not Found")))
                .map(AssetMapper.INSTANCE::toAssetDTO);
    }

    @Override
    public Mono<AssetDto> create(AssetDto assetDto) {
        return assetRepository.save(Asset.from(assetDto)
                        .createdAt(LocalDateTime.now())
                        .active(true)
                        .build())
                .map(AssetMapper.INSTANCE::toAssetDTO);

    }

    @Override
    public Mono<AssetDto> update(AssetDto assetDto) {
        return assetRepository.findById(assetDto.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset Not Found")))
                .flatMap(existing -> {
                    AssetDto.update(existing, assetDto);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return assetRepository.save(existing);
                })
                .map(AssetMapper.INSTANCE::toAssetDTO);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return assetRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset Not Found")))
                .flatMap(assetRepository::delete);
    }

    @Override
    public Mono<PageResponse<AssetDto>> findPagination(Integer pageSize, Integer pageNumber, LocalDate startDate, LocalDate endDate, String search, Boolean active, String filter) {
        Criteria criteria = Criteria.empty();

        if (active != null) {
            criteria = criteria.or(Criteria.where(Asset.IS_ACTIVE_COLUMN).is(active));
        }

        if (filter != null && !filter.isBlank()) {
            criteria = criteria.or(Criteria.where(Asset.ASSET_TYPE_COLUMN).is(filter))
                    .or(Criteria.where(Asset.TYPE_COLUMN).is(filter));
        }

        if (startDate != null && endDate != null) {
            criteria = criteria.and(Asset.PURCHASE_DATE_COLUMN).between(startDate, endDate);
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria
                    .where(Asset.BRAND_COLUMN).like(pattern)
                    .or(Asset.NAME_COLUMN).like(pattern)
                    .or(Asset.USER_COLUMN).like(pattern)
                    .or(Asset.CODE_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Asset.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.desc(Asset.PURCHASE_DATE_COLUMN)),
                AssetMapper.INSTANCE::toAssetDTO
        );
    }

    @Override
    public Mono<Map<String, Long>> count() {
        return assetRepository.count()
                .map(count -> Map.of("total", count));
    }
}
