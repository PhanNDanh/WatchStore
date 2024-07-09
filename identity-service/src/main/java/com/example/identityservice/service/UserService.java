package com.example.identityservice.service;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.identity.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);
}
