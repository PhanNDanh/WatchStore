package com.example.profileservice.mapper;

import com.example.commonmodels.profile.request.ProfileCreateRequest;
import com.example.commonmodels.profile.response.ProfileResponse;
import com.example.profileservice.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile createToEntity(ProfileCreateRequest request);

    ProfileResponse entityToResponse(Profile entity);

}
