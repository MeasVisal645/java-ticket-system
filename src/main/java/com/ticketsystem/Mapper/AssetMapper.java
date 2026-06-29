package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AssetDto;
import com.ticketsystem.Entities.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);
    AssetDto toAssetDTO(Asset asset);
}

