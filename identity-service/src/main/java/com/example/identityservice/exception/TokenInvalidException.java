package com.example.identityservice.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class TokenInvalidException extends BadCredentialsException {

    public TokenInvalidException(String msg) {
        super(msg);
    }
}
