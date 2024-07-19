package com.example.commonmodels.profile.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreateRequest {

    Long userId;

    String firstName;

    String lastName;

    LocalDate dob;

    String city;

}
