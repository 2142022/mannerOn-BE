package com.manneron.manneron.common.config;

import com.manneron.manneron.common.jwt.JwtUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;


import java.util.List;

/**
 * http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Manner-On")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI mannerOnCloneAPI() {
        Info info = new Info().title("Manner-On")
                .description("Manner-On")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        String access_token_header = JwtUtil.ACCESS_TOKEN;
        String refresh_token_header = JwtUtil.REFRESH_TOKEN;

        // 헤더에 security scheme 도 같이 보내게 만드는 것
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(access_token_header).addList(refresh_token_header);

        Components components = new Components()
                .addSecuritySchemes(access_token_header,
                        new SecurityScheme()
                                .name(access_token_header)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                .addSecuritySchemes(refresh_token_header,
                        new SecurityScheme()
                                .name(refresh_token_header)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}