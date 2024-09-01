package org.alfadev.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfadev.mainservice.dto.user.*;
import org.alfadev.mainservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseShortDto>> findAll(@RequestParam int page, @RequestParam int size) {
        log.info("");
        var result = userService.findAll(page, size);
        log.info("");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        log.info("");
        var result = userService.findById(id);
        log.info("");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDto) {
        log.info("");
        var result = userService.create(createDto);
        log.info("");

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateDto updateDto) {
        log.info("");
        var result = userService.update(updateDto);
        log.info("");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePasswordDto passwordDto) {
        log.info("");
        userService.changePassword(passwordDto);
        log.info("");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("");
        userService.deleteById(id);
        log.info("");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
