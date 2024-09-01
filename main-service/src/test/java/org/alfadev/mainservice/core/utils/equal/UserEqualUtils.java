package org.alfadev.mainservice.core.utils.equal;

import org.alfadev.mainservice.dto.user.UserCreateDto;
import org.alfadev.mainservice.dto.user.UserResponseDto;
import org.alfadev.mainservice.dto.user.UserUpdateDto;
import org.alfadev.mainservice.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.*;

public abstract class UserEqualUtils {

    public static void equal(UserResponseDto responseDto, UserEntity entity) {
        assertEquals(entity.getId(), responseDto.getId());
        assertEquals(entity.getName(), responseDto.getName());
        assertEquals(entity.getLastName(), responseDto.getLastName());
        assertEquals(entity.getEmail(), responseDto.getEmail());
        assertEquals(entity.getPatronymic(), responseDto.getPatronymic());
    }

    public static void equal(UserResponseDto responseDto, UserCreateDto createDto) {
        assertEquals(createDto.getEmail(), responseDto.getEmail());
        assertEquals(createDto.getName(), responseDto.getName());
        assertEquals(createDto.getPatronymic(), responseDto.getPatronymic());
        assertEquals(createDto.getLastName(), responseDto.getLastName());
    }

    public static void equal(UserResponseDto responseDto, UserUpdateDto updateDto) {
        assertEquals(updateDto.getId(), responseDto.getId());
        assertEquals(updateDto.getName(), responseDto.getName());
        assertEquals(updateDto.getPatronymic(), responseDto.getPatronymic());
        assertEquals(updateDto.getLastName(), responseDto.getLastName());
    }
}
