package kea.dpang.hrserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(Spring Docs) 설정
 */
@OpenAPIDefinition(
        info = @Info(title = "DPANG 인사팀 API 명세서",
                description = "DPANG 인사팀 API 명세서",
                version = "1.1.0")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("DPANG 인사팀 서비스 API")
                .pathsToMatch(paths)
                .build();
    }
}