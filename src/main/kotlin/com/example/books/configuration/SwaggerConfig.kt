package com.example.books.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

@Configuration
class SwaggerConfig {

    @Bean
    fun usersGroup(@Value("\${springdoc.version}") appVersion: String?): GroupedOpenApi? {
        return GroupedOpenApi.builder().group("books")
            .addOperationCustomizer { operation: Operation, handlerMethod: HandlerMethod? ->
                operation.addSecurityItem(SecurityRequirement().addList("basicScheme"))
                operation
            }
            .addOpenApiCustomizer { openApi: OpenAPI ->
                openApi.info(
                    Info().title("Users API").version(appVersion)
                )
            }
            .packagesToScan("com.example.books")
            .build()
    }
}