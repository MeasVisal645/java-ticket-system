package com.ticketsystem.Repository;

import com.ticketsystem.Entities.Asset;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends R2dbcRepository<Asset, Long> {
}
