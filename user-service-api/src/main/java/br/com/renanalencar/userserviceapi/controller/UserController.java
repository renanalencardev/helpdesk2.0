package br.com.renanalencar.userserviceapi.controller;

import br.com.renanalencar.userserviceapi.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "UserController", description = "Controller responsável pelas operações relacionadas aos usuários")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes do usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(
                    responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(
                    responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = StandardError.class)
            ))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "ID do usuário a ser buscado", required = true, example = "68989433c4ed1ad73c2805da")
            @PathVariable(name = "id") final String id);
}
