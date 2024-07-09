package com.example.identityservice.security;

import com.example.identityservice.constant.HeaderConstant;
import com.example.identityservice.security.provider.jwt.JwtAuthenticationToken;
import com.example.identityservice.security.provider.jwt.JwtAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        if (Objects.nonNull(jwt)) {
            Authentication authentication = jwtProvider.authenticate(new JwtAuthenticationToken(jwt));
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authentication.getPrincipal(), jwt, authentication.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        String languageHeader = request.getHeader(HeaderConstant.ACCEPT_LANGUAGE);

        if (Objects.nonNull(languageHeader)) {
            Locale locale = Locale.forLanguageTag(languageHeader);
            LocaleContextHolder.setLocale(locale);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HeaderConstant.AUTHORIZATION);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
