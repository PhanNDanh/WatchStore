package com.example.identityservice.mapper;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.profile.request.ProfileCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "userId", target = "userId")
    ProfileCreateRequest userCreateToProfileCreate(UserCreateRequest userCreate,Long userId);

}
