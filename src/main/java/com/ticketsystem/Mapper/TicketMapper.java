package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.TicketDto;
import com.ticketsystem.Entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    TicketDto toDto(Ticket ticket);
    Ticket toEntity(TicketDto ticketDto);
}
