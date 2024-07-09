package com.example.identityservice.mapper;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.identity.response.UserResponse;
import com.example.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User createToEntity(UserCreateRequest request);

    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "permissionGroups", source = "permissionGroups")
    @Mapping(target = "permissions", source = "permissions")
    UserResponse userToUserResponse(User user, Set<String> roles, Set<String> permissionGroups, Set<String> permissions);
}
