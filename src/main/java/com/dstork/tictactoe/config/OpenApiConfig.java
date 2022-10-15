package com.dstork.tictactoe.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8090/swagger-ui.html


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Tic Tac Toe - Spring Boot Swagger Configuration")
                        .description("\"Swagger configuration for application \""));
    }
}
