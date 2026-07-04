package com.diarychat.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI diaryChatOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Diary Chat API")
                        .description("Diary Chat 백엔드 API 문서")
                        .version("v1"));
    }
}
