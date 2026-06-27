package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Entities.Department;
import com.ticketsystem.Mapper.DepartmentMapper;
import com.ticketsystem.Repository.DepartmentRepository;
import com.ticketsystem.Service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .map(DepartmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<DepartmentDto> findById(Long id) {
        return departmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found")))
                .map(DepartmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<DepartmentDto> create(DepartmentDto departmentDto) {
        return departmentRepository.save(Department.from(departmentDto)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                .build())
                .map(DepartmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<DepartmentDto> update(DepartmentDto departmentDto) {
        return departmentRepository.findById(departmentDto.getId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found")))
                .flatMap(existing -> {
                    DepartmentDto.update(existing, departmentDto);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return departmentRepository.save(existing);
                })
                .map(DepartmentMapper.INSTANCE::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return departmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found")))
                .flatMap(departmentRepository::delete);
    }

    @Override
    public Mono<PageResponse<DepartmentDto>> findPagination(Integer pageSize, Integer pageNumber, String search, Boolean isActive) {
        Criteria criteria = Criteria.empty();

        if (isActive != null) {
            criteria = criteria.or(Criteria.where(Department.IS_ACTIVE_COLUMN).is(isActive));
        }

        if (search != null && !search.isBlank()) {
            String pattern = "%" + search.trim() + "%";
            Criteria searchCriteria = Criteria.where(Department.NAME_COLUMN).like(pattern);
            criteria = criteria.and(searchCriteria);
        }

        return FilterPaginationUtils.fetchSimple(
                r2dbcEntityTemplate,
                Department.class,
                criteria,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Sort.by(Sort.Order.asc(Department.ID_COLUMN)),
                DepartmentMapper.INSTANCE::toDto
        );
    }
}
