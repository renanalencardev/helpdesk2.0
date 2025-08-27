package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With // A anotação @With permite a criação de métodos de acesso para cada campo, seguindo o padrão de imutabilidade
// Isso significa que você pode criar uma nova instância de CreateUserRequest com um ou mais campos alterados, mantendo a instância original inalterada
// Por exemplo, se você tiver uma instância de CreateUserRequest chamada request, você pode criar uma nova instância com um campo
// alterado usando request.withName("Novo Nome"). Isso é útil para manter a imutabilidade dos objetos e evitar efeitos colaterais indesejados
public record CreateUserRequest(
        @Schema(description = "Nome do usuário", example = "Renan Alencar")
        @NotBlank(message = "O campo nome é obrigatório")
        @Size(min = 3, max = 50, message = "O campo nome deve ter entre 3 e 50 caracteres")
        String name,
        @Schema(description = "Email do usuário", example = "exemplo@mail.com")
        @Email(message = "Email inválido")
        @NotBlank(message = "O campo email é obrigatório")
        @Size(min = 6, max = 50, message = "O campo email deve ter entre 6 e 50 caracteres")
        String email,
        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "O campo senha é obrigatório")
        @Size(min = 6, max = 50, message = "O campo senha deve ter entre 6 e 50 caracteres")
        String password,
        @Schema(description = "Perfis do usuário", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\"]")
        Set<ProfileEnum> profiles
) {
}
