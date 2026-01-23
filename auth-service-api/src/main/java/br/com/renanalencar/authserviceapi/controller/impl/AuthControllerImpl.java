package br.com.renanalencar.authserviceapi.controller.impl;

import br.com.renanalencar.authserviceapi.controller.AuthController;
import br.com.renanalencar.authserviceapi.security.dtos.JWTAuthenticationImpl;
import br.com.renanalencar.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtils jwtUtils;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(final AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                        .authenticate(request)
                );
    }
}
