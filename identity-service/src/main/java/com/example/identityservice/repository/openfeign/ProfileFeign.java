package com.example.identityservice.repository.openfeign;

import com.example.commonmodels.profile.request.ProfileCreateRequest;
import com.example.commonmodels.profile.response.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service",url = "${app.services.profile}")
public interface ProfileFeign {

    @PostMapping(value = "/internal/user",produces = MediaType.APPLICATION_JSON_VALUE)
    ProfileResponse createProfile(@RequestBody ProfileCreateRequest createRequest);

}
