package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.TrademarkDto;
import com.ticketsystem.Entities.Trademark;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrademarkMapper {
    TrademarkMapper INSTANCE = Mappers.getMapper(TrademarkMapper.class);
    TrademarkDto toDto(Trademark trademark);
}
