package br.com.renanalencar.authserviceapi.security;

import br.com.renanalencar.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.renanalencar.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request){
        try {
            log.info("Autenticando usuário com email: {}", request.email());
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            return buildAuthenticateResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException e) {
            log.error("Erro ao autenticar usuário: {}", request.email());
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }

    protected AuthenticateResponse buildAuthenticateResponse(final UserDetailsDTO detailsDTO){
        log.info("Usuário autenticado com sucesso: {}", detailsDTO.getUsername());
        final var token = jwtUtil.generateToken(detailsDTO);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}
