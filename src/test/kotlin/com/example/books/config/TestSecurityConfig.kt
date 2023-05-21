package com.example.books.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import java.time.Instant


@TestConfiguration
class TestSecurityConfig {

    @Bean
    @Profile("test")
    fun jwtDecoder(): JwtDecoder {
        println("setting up BS security")
        return MockJwtDecoder()
    }

    class MockJwtDecoder: JwtDecoder {
        override fun decode(token: String?): Jwt {
            return Jwt(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(30),
                mapOf("alg" to "nope"),
                mapOf("a" to "b")
            )
        }
    }
}