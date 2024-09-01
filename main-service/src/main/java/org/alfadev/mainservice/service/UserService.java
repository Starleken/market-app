package org.alfadev.mainservice.service;

import org.alfadev.mainservice.dto.user.*;
import org.alfadev.mainservice.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponseShortDto> findAll(int page, int size);

    UserResponseDto findById(Long id);

    UserResponseDto create(UserCreateDto createDto);

    UserResponseDto update(UserUpdateDto updateDto);

    void changePassword(UserChangePasswordDto changePasswordDto);

    void deleteById(Long id);
}
