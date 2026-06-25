package com.qurio.server.services;

import com.qurio.server.dtos.UserDto;
import com.qurio.server.entities.Provider;
import com.qurio.server.entities.User;
import com.qurio.server.exceptions.ResourceNotFoundExeption;
import com.qurio.server.helper.UserHelper;
import com.qurio.server.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = modelMapper.map(userDto, User.class);

        if (userDto.getProvider() != null) {
            user.setProvider(userDto.getProvider());
        } else {
            user.setProvider(Provider.LOCAL);
        }

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExeption("User not found with the given email: " + email));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundExeption("User not found with the given userId: " + userId));

        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getImage() != null) existingUser.setImage(userDto.getImage());
        if (userDto.getProvider() != null) existingUser.setProvider(userDto.getProvider());
        if (userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());

        existingUser.setEnable(userDto.isEnable());
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundExeption("User not found with the given userId: " + userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundExeption("User not found with the given userId: " + userId));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
