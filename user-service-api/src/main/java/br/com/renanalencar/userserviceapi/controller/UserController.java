package br.com.renanalencar.userserviceapi.controller;

import br.com.renanalencar.userserviceapi.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "UserController", description = "Controller responsável pelas operações relacionadas aos usuários")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes do usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(
                    responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(
                    responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            ))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "ID do usuário a ser buscado", required = true, example = "68989433c4ed1ad73c2805da")
            @PathVariable(name = "id") final String id);

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(
                    responseCode = "400", description = "Requisição inválida",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(
                    responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            ))
    })
    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody final CreateUserRequest createUserRequest);

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = StandardError.class))
                    )),
            @ApiResponse(
                    responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
                    ))
    })
    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();

    @Operation(summary = "Atualizar um usuário", description = "Atualiza os dados do usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Requisição inválida",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(
                    responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(
                    responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = StandardError.class)
            ))
    })
    @PutMapping("/{id}")
    ResponseEntity<UserResponse> update(
            @Parameter(description = "ID do usuário", required = true, example = "68989433c4ed1ad73c2805da")
            @PathVariable(name = "id") final String id,
            @Valid @RequestBody final UpdateUserRequest updateUserRequest);
}
