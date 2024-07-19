package com.example.profileservice.service.impl;

import com.example.commonmodels.profile.request.ProfileCreateRequest;
import com.example.commonmodels.profile.response.ProfileResponse;
import com.example.profileservice.entity.Profile;
import com.example.profileservice.mapper.ProfileMapper;
import com.example.profileservice.repository.ProfileRepository;
import com.example.profileservice.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileServiceImpl implements ProfileService {

    ProfileRepository profileRepository;

    ProfileMapper profileMapper;

    @Override
    public ProfileResponse createProfile(ProfileCreateRequest request) {

        Profile profile = profileMapper.createToEntity(request);

        profile = profileRepository.save(profile);

        return profileMapper.entityToResponse(profile);
    }

    @Override
    public ProfileResponse getProfile(String id) {
        Profile userProfile = profileRepository.findById(id).orElseThrow(() -> (new RuntimeException("Profile not found")));
        return profileMapper.entityToResponse(userProfile);
    }
}
