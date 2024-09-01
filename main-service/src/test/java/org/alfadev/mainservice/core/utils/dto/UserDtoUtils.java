package org.alfadev.mainservice.core.utils.dto;

import org.alfadev.mainservice.core.utils.FakerUtils;
import org.alfadev.mainservice.dto.user.UserChangePasswordDto;
import org.alfadev.mainservice.dto.user.UserCreateDto;
import org.alfadev.mainservice.dto.user.UserUpdateDto;

import static org.alfadev.mainservice.core.utils.FakerUtils.*;

public abstract class UserDtoUtils {

    public static UserCreateDto generateUserCreateDto() {
        return UserCreateDto.builder()
                .email(FAKER.internet().emailAddress())
                .lastName(FAKER.name().lastName())
                .name(FAKER.name().name())
                .password(FAKER.internet().password())
                .patronymic(FAKER.name().suffix())
                .build();
    }

    public static UserUpdateDto generateUserUpdateDto(Long id) {
        return UserUpdateDto.builder()
                .id(id)
                .lastName(FAKER.name().lastName())
                .name(FAKER.name().name())
                .patronymic(FAKER.name().suffix())
                .build();
    }

    public static UserChangePasswordDto generateChangePasswordDto(Long id, String oldPassword) {
        return UserChangePasswordDto.builder()
                .id(id)
                .oldPassword(oldPassword)
                .newPassword(FAKER.internet().password())
                .build();
    }
}
