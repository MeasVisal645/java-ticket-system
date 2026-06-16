package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Assignments;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends R2dbcRepository<Assignments, Long> {
}
