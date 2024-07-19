package com.example.profileservice.service;

import com.example.commonmodels.profile.request.ProfileCreateRequest;
import com.example.commonmodels.profile.response.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileCreateRequest request);

    ProfileResponse getProfile(String id);
}
