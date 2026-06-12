package com.qurio.server.services;

import com.qurio.server.dtos.UserDto;
import com.qurio.server.entities.User;

public interface UserService {
    //create user
    UserDto createUser(UserDto userDto);

    //get user by email
    UserDto getUserByEmail(String email);

    //update user
    UserDto updateUser(UserDto userDto, String userId);

    //delete user
    void deleteUser(String userId);

    //get user by id
    UserDto getUserById(String userId);

    // get all Users
    Iterable<UserDto> getAllUsers();
}
