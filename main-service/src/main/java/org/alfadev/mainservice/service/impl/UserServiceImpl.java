package org.alfadev.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.alfadev.mainservice.dto.user.*;
import org.alfadev.mainservice.entity.UserEntity;
import org.alfadev.mainservice.mapper.UserMapper;
import org.alfadev.mainservice.repository.UserRepository;
import org.alfadev.mainservice.service.EncodingService;
import org.alfadev.mainservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.alfadev.mainservice.utils.ExceptionUtils.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EncodingService encodingService;

    @Override
    public Page<UserResponseShortDto> findAll(int page, int size) {
        var pageable = PageRequest.of(page - 1, size);
        var foundEntities = userRepository.findAll(pageable);
        var entitiesList = foundEntities.stream()
                .map(userMapper::mapToShortDto)
                .toList();

        return new PageImpl<>(entitiesList, pageable, foundEntities.getTotalElements());
    }

    @Override
    public UserResponseDto findById(Long id) {
        var found = userRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, id));

        return userMapper.mapToDto(found);
    }

    @Override
    public UserResponseDto create(UserCreateDto createDto) {
        var entityToSave = userMapper.mapToEntity(createDto);
        checkIfEmailExists(createDto.getEmail());
        entityToSave.setPassword(encodingService.encode(createDto.getPassword()));
        var saved = userRepository.save(entityToSave);

        return userMapper.mapToDto(saved);
    }

    @Override
    public UserResponseDto update(UserUpdateDto updateDto) {
        var foundEntity = userRepository.findById(updateDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, updateDto.getId()));

        userMapper.update(updateDto, foundEntity);
        var updated = userRepository.save(foundEntity);
        return userMapper.mapToDto(updated);
    }

    @Override
    public void changePassword(UserChangePasswordDto changePasswordDto) {
        var foundEntity = userRepository.findById(changePasswordDto.getId())
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, changePasswordDto.getId()));

        if (!encodingService.isMatch(changePasswordDto.getOldPassword(), foundEntity.getPassword())) {
            throwIllegalActionException("Passwords are not match");
        }

        foundEntity.setPassword(encodingService.encode(changePasswordDto.getNewPassword()));
        userRepository.save(foundEntity);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> getEntityNotFoundException(UserEntity.class, id));

        userRepository.deleteById(id);
    }

    private void checkIfEmailExists(String email) {
        var found = userRepository.findUserEntityByEmail(email);
        if (found.isPresent()) {
            throwIllegalActionException("Email " + email + " is used");
        }
    }
}
