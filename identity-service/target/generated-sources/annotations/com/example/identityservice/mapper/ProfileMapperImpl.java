package com.example.identityservice.mapper;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.profile.request.ProfileCreateRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileCreateRequest userCreateToProfileCreate(UserCreateRequest userCreate, Long userId) {
        if ( userCreate == null && userId == null ) {
            return null;
        }

        ProfileCreateRequest.ProfileCreateRequestBuilder profileCreateRequest = ProfileCreateRequest.builder();

        if ( userCreate != null ) {
            profileCreateRequest.firstName( userCreate.getFirstName() );
            profileCreateRequest.lastName( userCreate.getLastName() );
            profileCreateRequest.dob( userCreate.getDob() );
            profileCreateRequest.city( userCreate.getCity() );
        }
        profileCreateRequest.userId( userId );

        return profileCreateRequest.build();
    }
}
