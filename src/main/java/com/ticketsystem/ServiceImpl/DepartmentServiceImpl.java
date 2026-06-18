package com.ticketsystem.ServiceImpl;

import com.ticketsystem.Dto.DepartmentDto;
import com.ticketsystem.Entities.Department;
import com.ticketsystem.Mapper.DepartmentMapper;
import com.ticketsystem.Repository.DepartmentRepository;
import com.ticketsystem.Service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;


    @Override
    public Flux<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .map(DepartmentMapper::toDto);
    }

    @Override
    public Mono<DepartmentDto> findById(Long id) {
        return departmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found")))
                .map(DepartmentMapper::toDto);
    }

    @Override
    public Mono<DepartmentDto> create(DepartmentDto departmentDto) {
        return departmentRepository.save(Department.from(departmentDto)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .build())
                .map(DepartmentMapper::toDto);
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
                .map(DepartmentMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return departmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Department Not Found")))
                .flatMap(departmentRepository::delete);
    }
}
