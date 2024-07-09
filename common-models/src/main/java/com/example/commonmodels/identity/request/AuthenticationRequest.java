package com.example.commonmodels.identity.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationRequest {

    String username;

    String password;

    Boolean rememberMe;
}
