package models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
    @Size(min = 16, max = 50, message = "O refresh token deve ter entre 16 e 50 caracteres")
    @NotBlank(message = "O refresh token não pode ser vazio")
    String refreshToken
) {
}
