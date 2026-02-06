package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest(
        @Schema(description = "ID do solicitante", example = "na42df7a8sd76fadf871dsf3")
        @Size(min = 24, max = 36, message = "O campo requesterId deve ter entre 24 e 36 caracteres")
        String requesterId,
        @Schema(description = "ID do cliente", example = "cb12df7a8sd76fadf871dsf9")
        @Size(min = 24, max = 36, message = "O campo customerId deve ter entre 24 e 36 caracteres")
        String customerId,
        @Schema(description = "Título da ordem", example = "Instalação de software")
        @Size(min = 3, max = 45, message = "O campo title deve ter entre 3 e 45 caracteres")
        String title,
        @Schema(description = "Descrição da ordem", example = "Instalação do software XYZ na máquina do cliente")
        @Size(min = 10, max = 3000, message = "O campo description deve ter entre 10 e 3000 caracteres")
        String description,
        @Schema(description = "Status da ordem", example = "Open")
        @Size(min = 4, max = 15, message = "O campo status deve ter entre 4 e 15 caracteres")
        String status
) {
}
