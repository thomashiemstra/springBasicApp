package com.example.books.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth0")
data class Auth0Properties(
        val domain: String,
        val clientId: String,
        val clientSecret: String,
        val audience: String,
        val issuer: String
)