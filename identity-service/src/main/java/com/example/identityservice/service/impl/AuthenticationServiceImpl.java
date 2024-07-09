package com.example.identityservice.service.impl;

import com.example.commonmodels.identity.request.AuthenticationRequest;
import com.example.commonmodels.identity.request.IntrospectRequest;
import com.example.commonmodels.identity.request.LogoutRequest;
import com.example.commonmodels.identity.request.RefreshRequest;
import com.example.commonmodels.identity.response.AuthenticationResponse;
import com.example.commonmodels.identity.response.IntrospectResponse;
import com.example.identityservice.constant.MessageCode;
import com.example.identityservice.entity.InvalidatedToken;
import com.example.identityservice.exception.TokenInvalidException;
import com.example.identityservice.repository.InvalidatedTokenRepository;
import com.example.identityservice.security.provider.dao.DaoAuthenticationProviderCustom;
import com.example.identityservice.security.provider.jwt.JwtAuthenticationToken;
import com.example.identityservice.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt.secret}")
    private String SECRET;

    public static String AUTHORITIES = "Authorities";

    private final DaoAuthenticationProviderCustom daoAuthenticationProviderCustom;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        String username = request.getUsername().trim();
        String password = request.getPassword().trim();

        Boolean rememberMe = (Objects.nonNull(request.getRememberMe()) && !request.getRememberMe()) ? Boolean.FALSE : Boolean.TRUE;

        Authentication authentication = daoAuthenticationProviderCustom.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return AuthenticationResponse.builder()
                .token(generateToken(authentication, rememberMe))
                .expiryTime(generateExpirationDate())
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {

        return IntrospectResponse.builder()
                .valid(verifyToken(request.getToken()))
                .build();
    }

    @Override
    public void logout(LogoutRequest request) {

        String token = request.getToken();
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .token(token)
                .expiryTime(claims.getExpiration())
                .username(claims.getSubject())
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String oldToken = request.getToken();
        if (!verifyToken(oldToken)) {
            throw new TokenInvalidException(MessageCode.Commom.TOKEN_ERROR);
        }

        // Lưu token cũ vào Redis
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(oldToken).getBody();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .token(oldToken)
                .expiryTime(claims.getExpiration())
                .username(claims.getSubject())
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        // Extract thông tin từ token cũ để tạo Authentication
        Authentication authentication = extractAuthentication(oldToken);

        // Tạo token mới
        String newToken = generateToken(authentication, Boolean.FALSE);

        return AuthenticationResponse.builder()
                .token(newToken)
                .expiryTime(generateExpirationDate())
                .build();
    }

    public String generateToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date expirationDate;
        if (rememberMe) {
            expirationDate = generateExpirationDateForRememberMe();
        } else {
            expirationDate = generateExpirationDate();
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES, authorities)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setExpiration(expirationDate)
                .compact();
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)); // 1 day
    }

    public Date generateExpirationDateForRememberMe() {
        return new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)); // 1 month
    }

    public Authentication extractAuthentication(String oldToken) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(oldToken).getBody();

        // Lấy thông tin từ token cũ
        String username = claims.getSubject();
        List<GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(authority -> new SimpleGrantedAuthority(authority.trim()))
                .collect(Collectors.toList());   ;

        // Tạo đối tượng Authentication
        return new JwtAuthenticationToken(username, null, authorities);
    }

    public boolean verifyToken(String token) {
        try {

            Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            // Kiểm tra xem token đã bị vô hiệu hóa hay chưa
            if (invalidatedTokenRepository.existsById(token)) {
                return false;
            }

            return true;
        } catch (JwtException e) {
            return false;
        }
    }


}
