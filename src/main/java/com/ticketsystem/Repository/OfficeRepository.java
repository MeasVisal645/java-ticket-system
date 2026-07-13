package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Office;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends R2dbcRepository<Office, Long> {
}
