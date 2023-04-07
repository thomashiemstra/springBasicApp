package com.example.books.configuration

import com.example.books.security.Auth0Properties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class Auth0Config(val auth0Properties: Auth0Properties) {

    @Bean
    fun auth0RestTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.rootUri(auth0Properties.issuer).build()
    }

}