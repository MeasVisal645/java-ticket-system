package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.OfficeDto;
import com.ticketsystem.Entities.Office;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OfficeMapper {
    OfficeMapper INSTANCE = Mappers.getMapper(OfficeMapper.class);
    OfficeDto toDto(Office office);
}
