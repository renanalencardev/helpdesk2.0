package models.responses;

import models.enums.ProfileEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public record UserResponse(
    String id,
    String name,
    String email,
    String password,
    Set<ProfileEnum> profiles
) implements Serializable {
    // Implementa Serializable para permitir que UserResponse seja convertido em bytes (serialização)
    // Isso é útil para persistência, cache ou transmissão de objetos
    @Serial
    private static final long serialVersionUID = 1L; // Garante compatibilidade entre versões da classe na desserialização
}
