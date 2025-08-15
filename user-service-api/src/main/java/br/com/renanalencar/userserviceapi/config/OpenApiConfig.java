package br.com.renanalencar.userserviceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean // O BEAN no Spring é um objeto gerenciado pelo container do Spring
    // Sempre que o projeto iniciar o metodo customOpenAPI será chamado
    // O OpenAPI é uma especificação para descrever APIs RESTful
    // O OpenAPI é usado para gerar documentação interativa e fornecer informações sobre a API'
    public OpenAPI customOpenAPI(
            @Value("${springdoc.openapi.title}") final String title,
            @Value("${springdoc.openapi.description}") final String description,
            @Value("${springdoc.openapi.version}") final String version
    ) {
        return new OpenAPI()
                        .info(new Info()
                            .title(title)
                            .description(description)
                            .version(version));
    }
}