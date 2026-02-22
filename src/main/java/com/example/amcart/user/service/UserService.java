package com.example.amcart.user.service;

import com.example.amcart.user.dto.RegisterRequest;
import com.example.amcart.responses.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
    UserResponse getCurrentUser();
}
