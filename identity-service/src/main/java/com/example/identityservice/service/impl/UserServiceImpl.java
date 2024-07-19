package com.example.identityservice.service.impl;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.identity.response.UserResponse;
import com.example.identityservice.constant.RoleUser;
import com.example.identityservice.entity.User;
import com.example.identityservice.mapper.ProfileMapper;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.*;
import com.example.identityservice.repository.openfeign.ProfileFeign;
import com.example.identityservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    RoleUserRepository roleUserRepository;

    PermissionGroupRepository permissionGroupRepository;

    PermissionRepository permissionRepository;

    ProfileFeign profileFeign;

    UserMapper userMapper;

    ProfileMapper profileMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = userMapper.createToEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        var userId = user.getId();

        com.example.identityservice.entity.RoleUser roleUser = com.example.identityservice.entity.RoleUser.builder()
                .userId(userId)
                .roleId(Long.valueOf(RoleUser.STAFF.getId()))
                .build();

        roleUserRepository.save(roleUser);

        Set<String> roles = roleRepository.getRolesByUserId(userId);

        Set<String> permissionGroups = permissionGroupRepository.getPermissionGroupsByUserId(userId);

        Set<String> permissions = permissionRepository.getPermissionsByUserId(userId);

        var profileRequest =  profileMapper.userCreateToProfileCreate(request,userId);

        profileFeign.createProfile(profileRequest);

        return userMapper.userToUserResponse(user, roles, permissionGroups, permissions);
    }
}
