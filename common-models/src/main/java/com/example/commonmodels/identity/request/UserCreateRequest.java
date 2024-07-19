package com.example.commonmodels.identity.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    String username;

    String password;

    String rePassword;

    String firstName;

    String lastName;

    LocalDate dob;

    String city;
}
