package br.com.renanalencar.authserviceapi.services;

import br.com.renanalencar.authserviceapi.models.RefreshToken;
import br.com.renanalencar.authserviceapi.repositories.RefreshTokenRepository;
import br.com.renanalencar.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.renanalencar.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String username) {
        return repository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .createdAt(now())
                        .expiredAt(now().plusSeconds(refreshTokenExpirationSec))
                        .username(username)
                        .build());
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var refreshToken  = repository.findById(refreshTokenId)
                .orElseThrow(() -> new RuntimeException("Refresh token não encontrado. Id: " + refreshTokenId));
        if (refreshToken.getExpiredAt().isBefore(now())) {
            throw new RefreshTokenExpired("Refresh token expirado. Id: " + refreshTokenId);
        }
        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }
}
