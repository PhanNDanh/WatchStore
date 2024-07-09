package com.example.identityservice.service;

import com.example.commonmodels.identity.request.AuthenticationRequest;
import com.example.commonmodels.identity.request.IntrospectRequest;
import com.example.commonmodels.identity.request.LogoutRequest;
import com.example.commonmodels.identity.request.RefreshRequest;
import com.example.commonmodels.identity.response.AuthenticationResponse;
import com.example.commonmodels.identity.response.IntrospectResponse;

public interface AuthenticationService {

     AuthenticationResponse authenticate(AuthenticationRequest request);

     IntrospectResponse introspect(IntrospectRequest request);

     void logout(LogoutRequest request);

     AuthenticationResponse refreshToken(RefreshRequest request);
}
