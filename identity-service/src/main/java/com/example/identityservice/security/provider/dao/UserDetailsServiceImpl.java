package com.example.identityservice.security.provider.dao;

import com.example.identityservice.constant.MessageCode;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.UserDetailNotfoundException;
import com.example.identityservice.repository.PermissionRepository;
import com.example.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDetailNotfoundException(MessageCode.Account.ACCOUNT_NOT_FOUND));

        Set<String> permissionsById = permissionRepository.getPermissionsByUserId(user.getId());

        if(CollectionUtils.isEmpty(permissionsById)){
            throw new UserDetailNotfoundException(MessageCode.Account.PERMISSION_NOT_FOUND);
        }

        Set<SimpleGrantedAuthority> authorities = permissionsById.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return UserDetailsImpl.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
