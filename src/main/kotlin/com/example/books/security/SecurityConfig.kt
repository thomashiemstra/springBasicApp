package com.example.books.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.core.*
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(val auth0Properties: Auth0Properties) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth0/login", "/v3/api-docs/**","/swagger-ui/**", "/error").permitAll()
//                .requestMatchers("/admin").hasAnyAuthority("read:all")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer().jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                .and()
                .and().build()

    }

    fun jwtDecoder(): JwtDecoder {
        return (JwtDecoders.fromOidcIssuerLocation(auth0Properties.issuer) as NimbusJwtDecoder).apply {
            setJwtValidator(
                    DelegatingOAuth2TokenValidator(
                            AudienceValidator(auth0Properties.audience),
                            JwtValidators.createDefaultWithIssuer(auth0Properties.issuer)
                    )
            )
        }
    }

    fun jwtAuthenticationConverter(): JwtAuthenticationConverter? {
        val converter = JwtGrantedAuthoritiesConverter()
        converter.setAuthoritiesClaimName("permissions")
        converter.setAuthorityPrefix("")
        val jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter)
        return jwtConverter
    }
}

internal class AudienceValidator(private val audience: String): OAuth2TokenValidator<Jwt> {

    override fun validate(token: Jwt): OAuth2TokenValidatorResult {
        val audiences: List<String> = token.audience
        if (audiences.contains(audience)) {
            return OAuth2TokenValidatorResult.success()
        }
        return OAuth2TokenValidatorResult.failure(OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN))
    }

}
