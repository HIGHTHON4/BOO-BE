package com.example.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration // 스프링 실행시 설정파일 읽어드리기 위한 어노테이션
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("2025 해커톤 API")
            .description("토론한판 API")
            .version("1.0.0")
    }
}
