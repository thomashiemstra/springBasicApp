package com.example.books.service

import com.example.books.security.Auth0Properties
import org.generated.books.model.WebAuth0LoginRequest
import org.generated.books.model.WebAuth0LoginResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class Auth0Service(val auth0RestTemplate: RestTemplate, val auth0Properties: Auth0Properties) {

    fun login(request: WebAuth0LoginRequest): WebAuth0LoginResponse {

        val url = auth0Properties.issuer + "/oauth/token"

        val loginRequest = LoginRequest(
                grant_type = GRANT_TYPE,
                username = request.username,
                password = request.password,
                audience = auth0Properties.audience,
                client_id = auth0Properties.clientId,
                client_secret = auth0Properties.clientSecret
        )

        val response = auth0RestTemplate.postForObject(url, loginRequest, LoginResponse::class.java) ?: error("got no response from auth0")

        //todo mapper
        return WebAuth0LoginResponse().jwtToken(response.access_token)
    }

    //https://dev-xs1u5mnzrm83ke4o.us.auth0.com/oauth/token
    //{
    //  "grant_type":"password",
    //  "username":"thomas-hiemstra@hotmail.com",
    //  "password": "Admin@123",
    //  "audience":"https://books/api",
    //  "client_id":"0CM2y9a5ym9uE20hQvNp2ALr5lpRLr0E",
    //  "client_secret":"3ohzRndl_YUjv9jqucHGBiyXu_ek3dH52CAur4aRHhXK8UL0cx5y8WXH96IjBnlf"
    //}

    companion object {
        const val GRANT_TYPE = "password"
    }
}

data class LoginRequest(val grant_type: String, val username: String, val password: String, val audience: String, val client_id: String, val client_secret: String)

data class LoginResponse(val access_token: String)