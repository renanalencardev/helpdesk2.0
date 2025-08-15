package models.requests;

import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With // A anotação @With permite a criação de métodos de acesso para cada campo, seguindo o padrão de imutabilidade
// Isso significa que você pode criar uma nova instância de CreateUserRequest com um ou mais campos alterados, mantendo a instância original inalterada
// Por exemplo, se você tiver uma instância de CreateUserRequest chamada request, você pode criar uma nova instância com um campo
// alterado usando request.withName("Novo Nome"). Isso é útil para manter a imutabilidade dos objetos e evitar efeitos colaterais indesejados
public record CreateUserRequest(
        String name,
        String email,
        String password,
        Set<ProfileEnum> profiles
) {
}
