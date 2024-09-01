package org.alfadev.mainservice.mapper;

import org.alfadev.mainservice.dto.user.UserCreateDto;
import org.alfadev.mainservice.dto.user.UserResponseDto;
import org.alfadev.mainservice.dto.user.UserResponseShortDto;
import org.alfadev.mainservice.dto.user.UserUpdateDto;
import org.alfadev.mainservice.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapToEntity(UserCreateDto createDto);

    UserResponseDto mapToDto(UserEntity entity);

    UserResponseShortDto mapToShortDto(UserEntity entity);

    default void update(UserUpdateDto updateDto, UserEntity entityToUpdate) {
        entityToUpdate.setName(updateDto.getName());
        entityToUpdate.setLastName(updateDto.getLastName());
        entityToUpdate.setPatronymic(updateDto.getPatronymic());
    }
}
