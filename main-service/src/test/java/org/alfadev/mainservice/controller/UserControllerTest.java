package org.alfadev.mainservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.alfadev.mainservice.BaseIntegrationTest;
import org.alfadev.mainservice.RestPage;
import org.alfadev.mainservice.core.db.UserDbHelper;
import org.alfadev.mainservice.dto.user.UserResponseDto;
import org.alfadev.mainservice.dto.user.UserResponseShortDto;
import org.alfadev.mainservice.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import java.util.List;

import static org.alfadev.mainservice.core.utils.FakerUtils.*;
import static org.alfadev.mainservice.core.utils.dto.UserDtoUtils.*;
import static org.alfadev.mainservice.core.utils.equal.UserEqualUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserDbHelper userDbHelper;

    @BeforeEach
    void setUp() {
        userDbHelper.clearDb();
    }

    @Test
    void testFindAll_happyPath() throws Exception {
        //given
        var usersCount = 5;
        userDbHelper.saveUsers(usersCount);

        //when
        var mvcResult = mockMvc.perform(get("/users?page=1&size=" + usersCount))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, new TypeReference<RestPage<UserResponseShortDto>>() {
        });

        //then
        assertEquals(usersCount, response.getTotalElements());
    }

    @Test
    void testFindById_happyPath() throws Exception {
        //given
        var saved = userDbHelper.saveUser();

        //when
        var mvcResult = mockMvc.perform(get("/users/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(response, saved);
    }

    @Test
    void testFindById_whenUserNotFound() throws Exception {
        //given
        var idToSearch = 1L;

        //when
        mockMvc.perform(get("/users/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testCreate_happyPath() throws Exception {
        //given
        var createDto = generateUserCreateDto();

        //when
        var mvcResult = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(response, createDto);
    }

    @Test
    void testCreate_whenEmailExists() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();
        var createDto = generateUserCreateDto();
        createDto.setEmail(savedUser.getEmail());

        //when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testUpdate_happyPath() throws Exception {
        //given
        var savedUser = userDbHelper.saveUser();
        var updateDto = generateUserUpdateDto(savedUser.getId());

        //when
        var mvcResult = mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andReturn();

        var bytes = mvcResult.getResponse().getContentAsByteArray();
        var response = objectMapper.readValue(bytes, UserResponseDto.class);

        //then
        equal(response, updateDto);
    }

    @Test
    void testUpdate_whenUserNotFound() throws Exception {
        //given
        var idToSearch = 1L;
        var updateDto = generateUserUpdateDto(idToSearch);

        //when
        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangePassword_happyPath() throws Exception {
        //given
        var password = FAKER.internet().password();
        var savedUser = userDbHelper.saveUser(password);
        var changePasswordDto = generateChangePasswordDto(savedUser.getId(), password);

        //when
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isOk());

        var updatedUser = userDbHelper.findById(savedUser.getId());

        //then
        assertTrue(updatedUser.isPresent());
        assertNotEquals(savedUser.getPassword(), updatedUser.get().getPassword());
    }

    @Test
    void testChangePassword_whenUserNotFound() throws Exception {
        //given
        var idToSearch = 1L;
        var password = "password";
        var changePasswordDto = generateChangePasswordDto(idToSearch, password);

        //when
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isNotFound());

        //then
    }

    @Test
    void testChangePassword_whenPasswordsAreNotMatch() throws Exception {
        //given
        var password = FAKER.internet().password();
        var savedUser = userDbHelper.saveUser(password);
        var changePasswordDto = generateChangePasswordDto(savedUser.getId(), password + "fake");

        //when
        mockMvc.perform(put("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest());

        //then
    }

    @Test
    void testDeleteById_happyPath() throws Exception{
        //given
        var savedUser = userDbHelper.saveUser();

        //when
        mockMvc.perform(delete("/users/{id}", savedUser.getId()))
                .andExpect(status().isOk());

        var foundUser = userDbHelper.findById(savedUser.getId());

        //then
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void testDeleteById_whenUserNotFound() throws Exception {
        //given
        var idToSearch = 1L;

        //when
        mockMvc.perform(delete("/users/{id}", idToSearch))
                .andExpect(status().isNotFound());

        //then
    }
}
