package com.mobylab.springbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Ferma API", version = "1.0"),
        security = @SecurityRequirement(name = "Bearer Authentication") // Apply globally
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    @Profile("auth")
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/v1/auth/**",
                        "/api/v1/role/**")
                .build();
    }

    @Bean
    @Profile("backend")
    public GroupedOpenApi backendGroup() {
        return GroupedOpenApi.builder()
                .group("backend")
                // catch everything under /api/v1, regardless of singular/plural
                .pathsToMatch(
                        "/categories/**",
                        "/equipment/**",
                        "/api/v1/rentals/**"
                )
                .build();
    }



}
