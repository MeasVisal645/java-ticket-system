package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.CategoryDto;
import com.ticketsystem.Entities.Category;
import com.ticketsystem.Entities.History;
import com.ticketsystem.Mapper.CategoryMapper;
import com.ticketsystem.Repository.CategoryRepository;
import com.ticketsystem.Service.CategoryService;
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

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .map(CategoryMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<CategoryDto> create(CategoryDto categoryDto) {
        return categoryRepository.save(Category.from(categoryDto)
                        .isActive(true)
                .build())
                .map(CategoryMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<CategoryDto> update(CategoryDto categoryDto) {
        return categoryRepository.findById(categoryDto.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found")))
                .flatMap(existing -> {
                    CategoryDto.update(existing, categoryDto);
                    return categoryRepository.save(existing);
                })
                .map(CategoryMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found")))
                .flatMap(category -> categoryRepository.deleteById(id));
    }

    @Override
    public Mono<PageResponse<CategoryDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive) {
        Criteria criteria = Criteria.empty();

        if (isActive != null) {
            criteria = criteria.or(Criteria.where(Category.IS_ACTIVE_COLUMN).is(isActive));
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(History.TICKET_NO_COLUMN).like(pattern)
                    .or(History.SUBJECT_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Category.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.asc(Category.ID_COLUMN)),
                CategoryMapper.INSTANCE::toDto
        );
    }
}
