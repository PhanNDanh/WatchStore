package com.example.identityservice.security.provider.jwt;

import com.example.identityservice.constant.MessageCode;
import com.example.identityservice.exception.TokenInvalidException;
import com.example.identityservice.repository.InvalidatedTokenRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String SECRET;

    public static final String AUTHORITIES = "Authorities";

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public JwtAuthenticationProvider(InvalidatedTokenRepository invalidatedTokenRepository) {
        this.invalidatedTokenRepository = invalidatedTokenRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = String.valueOf(authentication.getPrincipal());

        // Kiểm tra xem token có bị vô hiệu hóa không
        if (invalidatedTokenRepository.existsById(jwt)) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_INVALIDATED);
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_UN_SUPPORT);
        } catch (MalformedJwtException e) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_MALFORMED);
        } catch (SignatureException e) {
            throw new TokenInvalidException(MessageCode.Commom.SIGNATURE_INVALID);
        } catch (IllegalArgumentException e) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_ERROR);
        }

        String authoritiesString = claims.get(AUTHORITIES, String.class);
        if (authoritiesString == null || authoritiesString.isEmpty()) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_ERROR);
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = Arrays
                .stream(authoritiesString.split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .toList();

        String subject = claims.getSubject();
        if (subject == null || subject.isEmpty()) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_ERROR);
        }

        return new JwtAuthenticationToken(subject, jwt, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


