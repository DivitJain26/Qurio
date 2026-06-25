package com.qurio.server.controllers;

import com.qurio.server.dtos.UserDto;
import com.qurio.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDt, @PathVariable String userId) {
        return ResponseEntity.ok(userService.updateUser(userDt, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
