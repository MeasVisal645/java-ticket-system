package com.ticketsystem.Repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.sql.Assignment;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends R2dbcRepository<Assignment, Long> {
}
