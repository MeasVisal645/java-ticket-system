package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.HistoryDto;
import com.ticketsystem.Dto.HistoryResponseDto;
import com.ticketsystem.Entities.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);
    HistoryDto toDto(History history);
    HistoryResponseDto toResponseDto(History history);
}
