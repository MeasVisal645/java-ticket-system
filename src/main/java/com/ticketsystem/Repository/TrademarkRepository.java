package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Trademark;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrademarkRepository extends R2dbcRepository<Trademark, Long> {
}
