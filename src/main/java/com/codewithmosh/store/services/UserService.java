package com.codewithmosh.store.services;


import com.codewithmosh.store.dtos.requests.RegisterUserRequest;
import com.codewithmosh.store.dtos.responses.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(RegisterUserRequest request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
