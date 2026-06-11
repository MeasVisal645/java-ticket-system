package com.ticketsystem.Mapper;

import com.ticketsystem.Dto.AttachmentDto;
import com.ticketsystem.Dto.UserDto;
import com.ticketsystem.Entities.Attachment;
import com.ticketsystem.Entities.User;

public class UserMapper {
    // Convert from Entity to DTO
    public static UserDto toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
//        dto.setImageKey(user.getImageKey());

        return dto;
    }

    // Convert from DTO to Entity
    public static User toEntity(UserDto dto) {

        if (dto == null) {
            return null;
        }

        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .role(dto.getRole())
//                .imageKey(dto.getImageKey())
                .build();
    }
}
