package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Dto.UsernameResponse;
import com.ticketsystem.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toDto(User user);
    User toEntity(UserDto userDto);

    UsernameResponse toUsernameResponseDto(User user);
}
