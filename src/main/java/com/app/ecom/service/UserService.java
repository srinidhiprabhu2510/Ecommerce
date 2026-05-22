package com.app.ecom.service;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponse> getUser(Long id);
    List<UserResponse> getAllUsers();
    void createUsers(UserRequest userRequest);
    boolean updatedUser(Long id, UserRequest updateUser);
}
