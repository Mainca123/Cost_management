package com.example.HeThongQuanLyTaiChinhThongMinh.config;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    private final MultipartFileArrayConverter multipartFileArrayConverter;

    @Bean
    public OpenAPI customOpenAPI() {
        ModelConverters.getInstance().addConverter(multipartFileArrayConverter);
        return new OpenAPI()
                .info(new Info()
                        .title("Simulation bank API")
                        .version("1.0")
                        .description("Documentation Simulation bank API v1.0"))
                .components(new Components().addSecuritySchemes(
                        SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name("Authorization")
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}
