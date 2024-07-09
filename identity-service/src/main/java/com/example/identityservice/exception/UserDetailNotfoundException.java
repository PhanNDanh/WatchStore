package com.example.identityservice.exception;

import org.springframework.security.core.AuthenticationException;

public class UserDetailNotfoundException extends AuthenticationException {

    public UserDetailNotfoundException(String msg) {
        super(msg);
    }
}
