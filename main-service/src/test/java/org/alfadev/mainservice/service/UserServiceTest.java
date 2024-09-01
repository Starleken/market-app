package org.alfadev.mainservice.service;

import org.alfadev.mainservice.exception.EntityNotFoundException;
import org.alfadev.mainservice.exception.IllegalActionException;
import org.alfadev.mainservice.mapper.UserMapper;
import org.alfadev.mainservice.repository.UserRepository;
import org.alfadev.mainservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.alfadev.mainservice.core.utils.dto.UserDtoUtils.*;
import static org.alfadev.mainservice.core.utils.entity.UserEntityUtils.*;
import static org.alfadev.mainservice.core.utils.equal.UserEqualUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private EncodingService encodingService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindById_happyPath() {
        //given
        var idToSearch = 1L;
        var generatedUser = generateUser(idToSearch);

        when(userRepository.findById(idToSearch)).thenReturn(Optional.of(generatedUser));

        //when
        var found = userService.findById(idToSearch);

        //then
        equal(found, generatedUser);
    }

    @Test
    void testCreate_happyPath() {
        //given
        var encoded = "encoded";
        var createDto = generateUserCreateDto();

        when(userRepository.findUserEntityByEmail(createDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(encodingService.encode(any())).thenReturn(encoded);

        //when
        var created = userService.create(createDto);

        //then
        equal(created, createDto);
    }

    @Test
    void testCreate_whenEmailExists() {
        //given
        var idToGenerate = 1L;
        var generatedEntity = generateUser(idToGenerate);
        var createDto = generateUserCreateDto();

        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.of(generatedEntity));

        //when
        assertThrows(IllegalActionException.class,
                () -> userService.create(createDto));

        //then
    }

    @Test
    void testUpdate_happyPath() {
        //given
        var idToUpdate = 1L;
        var generatedEntity = generateUser(idToUpdate);
        var updateDto = generateUserUpdateDto(idToUpdate);

        when(userRepository.findById(idToUpdate)).thenReturn(Optional.of(generatedEntity));
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        //when
        var updated = userService.update(updateDto);

        //then
        equal(updated, updateDto);
    }

    @Test
    void testUpdate_whenUserNotFound() {
        //given
        var idToUpdate = 1L;
        var updateDto = generateUserUpdateDto(idToUpdate);

        when(userRepository.findById(idToUpdate)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> userService.update(updateDto));

        //then
    }

    @Test
    void testChangePassword_happyPath() {
        //given
        var idToGenerate = 1L;
        var oldPassword = "password";
        var passwordDto = generateChangePasswordDto(idToGenerate, oldPassword);
        var generatedEntity = generateUser(idToGenerate);

        when(userRepository.findById(idToGenerate)).thenReturn(Optional.of(generatedEntity));
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(encodingService.isMatch(any(), any())).thenReturn(true);

        //when
        userService.changePassword(passwordDto);

        //then
        verify(userRepository).save(any());
    }

    @Test
    void testDeleteById_happyPath() {
        //given
        var idToDelete = 1L;
        var generatedUser = generateUser(idToDelete);

        when(userRepository.findById(idToDelete)).thenReturn(Optional.of(generatedUser));

        //when
        userService.deleteById(idToDelete);

        //then
        verify(userRepository).deleteById(idToDelete);
    }

    @Test
    void testDeleteById_whenUserNotFound() {
        //given
        var idToDelete = 1L;

        when(userRepository.findById(idToDelete)).thenReturn(Optional.empty());

        //when
        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteById(idToDelete));

        //then
    }
}
