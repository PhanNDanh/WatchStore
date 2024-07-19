package com.example.profileservice.controller;

import com.example.commonmodels.profile.request.ProfileCreateRequest;
import com.example.commonmodels.profile.response.ProfileResponse;
import com.example.profileservice.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalProfileController {

    ProfileService userProfileService;

    @PostMapping("/internal/user")
    ProfileResponse createProfile(@RequestBody ProfileCreateRequest request) {
        return userProfileService.createProfile(request);
    }
}
