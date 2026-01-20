package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record AuthenticateRequest(
        @Schema(description = "Email do usuário", example = "exemplo@mail.com")
        @Email(message = "Email inválido")
        @NotBlank(message = "O campo email é obrigatório")
        @Size(min = 6, max = 50, message = "O campo email deve ter entre 6 e 50 caracteres")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "O campo senha é obrigatório")
        @Size(min = 6, max = 50, message = "O campo senha deve ter entre 6 e 50 caracteres")
        String password
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
