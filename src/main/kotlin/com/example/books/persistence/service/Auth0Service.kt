package com.example.books.persistence.service

import com.example.books.persistence.UserRepository
import com.example.books.security.Auth0Properties
import org.generated.books.model.WebAuth0LoginRequest
import org.generated.books.model.WebAuth0LoginResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import com.example.books.persistence.*
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder


@Service
class Auth0Service(val auth0RestTemplate: RestTemplate,
                   val auth0Properties: Auth0Properties,
                   val userRepository: UserRepository
                   ) {

    fun login(request: WebAuth0LoginRequest): WebAuth0LoginResponse {

        val url = auth0Properties.issuer + "/oauth/token"

        val loginRequest = LoginRequest(
                grant_type = PASSWORD_GRANT_TYPE,
                username = request.username,
                password = request.password,
                audience = auth0Properties.audience,
                client_id = auth0Properties.clientId,
                client_secret = auth0Properties.clientSecret,
                scope = "read:sample"
        )

        val response = auth0RestTemplate.postForObject(url, loginRequest, LoginResponse::class.java) ?: error("got no response from auth0")
        val jwt = (JwtDecoders.fromOidcIssuerLocation(auth0Properties.issuer) as NimbusJwtDecoder).decode(response.access_token)
        validateTokenAudience(jwt)

        val userName = jwt.claims["sub"] as String
        saveUserIfNotExists(userName)

        return WebAuth0LoginResponse().jwtToken(response.access_token)
    }

    @Transactional
    private fun saveUserIfNotExists(userName: String) {
        userRepository.findByUserName(userName) ?: let {
            val newUser = User(null, userName)
            userRepository.save(newUser)
        }
    }

    private fun validateTokenAudience(jwt: Jwt) {
        val audiences = jwt.claims["aud"] as List<*>
        if (!audiences.contains(auth0Properties.audience)) throw java.lang.IllegalStateException("unknown audience")
    }

    companion object {
        const val PASSWORD_GRANT_TYPE = "password"
    }
}

data class LoginRequest(val grant_type: String, val username: String, val password: String, val audience: String, val client_id: String, val client_secret: String, val scope: String)

data class LoginResponse(val access_token: String)

data class InfoRequest(val id_token: String)

data class InfoResponse(val user_id: String)