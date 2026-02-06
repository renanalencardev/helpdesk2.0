package br.com.renanalencar.orderserviceapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateOderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Order Controller", description = "API para gerenciamento de ordens de serviço")
@RequestMapping("/api/orders")
public interface OrderController {

    @Operation(summary = "Criar uma nova ordem de serviço", description = "Endpoint para criar uma nova ordem de serviço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordem de serviço criada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            )
    })
    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody final CreateOderRequest request);


    @Operation(summary = "Atualizar uma ordem de serviço", description = "Endpoint para atualizar uma ordem de serviço existente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de serviço atualizada com sucesso", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(
            @Parameter(description = "Id da ordem de serviço", required = true, example = "10")
            @PathVariable(name = "id") final String id,
            @Parameter(description = "Atualizações da ordem de serviço", required = true)
            @Valid @RequestBody UpdateOrderRequest request
            );
}
