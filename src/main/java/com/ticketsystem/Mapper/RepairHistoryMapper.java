package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.RepairHistoryDto;
import com.ticketsystem.Entities.RepairHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RepairHistoryMapper {
    RepairHistoryMapper INSTANCE = Mappers.getMapper(RepairHistoryMapper.class);
    RepairHistoryDto toDto(RepairHistory repairHistory);
    RepairHistory toEntity(RepairHistoryDto repairHistoryDto);
}
