package com.example.identityservice.mapper;

import com.example.commonmodels.identity.request.UserCreateRequest;
import com.example.commonmodels.identity.response.UserResponse;
import com.example.identityservice.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User createToEntity(UserCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( request.getUsername() );
        user.password( request.getPassword() );

        return user.build();
    }

    @Override
    public UserResponse userToUserResponse(User user, Set<String> roles, Set<String> permissionGroups, Set<String> permissions) {
        if ( user == null && roles == null && permissionGroups == null && permissions == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        if ( user != null ) {
            if ( user.getId() != null ) {
                userResponse.setId( String.valueOf( user.getId() ) );
            }
            userResponse.setUsername( user.getUsername() );
        }
        Set<String> set = roles;
        if ( set != null ) {
            userResponse.setRoles( new LinkedHashSet<String>( set ) );
        }
        Set<String> set1 = permissionGroups;
        if ( set1 != null ) {
            userResponse.setPermissionGroups( new LinkedHashSet<String>( set1 ) );
        }
        Set<String> set2 = permissions;
        if ( set2 != null ) {
            userResponse.setPermissions( new LinkedHashSet<String>( set2 ) );
        }

        return userResponse;
    }
}
