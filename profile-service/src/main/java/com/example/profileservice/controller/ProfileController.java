package com.example.profileservice.controller;

import com.example.commonmodels.profile.response.ProfileResponse;
import com.example.profileservice.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProfileController {

    ProfileService profileService;

    @GetMapping("/{id}")
    ProfileResponse getProfile(@PathVariable("id") String id) {
        return  profileService.getProfile(id);
    }
}
