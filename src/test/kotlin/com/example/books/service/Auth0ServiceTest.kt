package com.example.books.service

import com.example.books.persistence.User
import com.example.books.persistence.UserRepository
import com.example.books.security.Auth0Properties
import org.generated.books.model.WebAuth0LoginRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.web.client.RestTemplate


internal class Auth0ServiceTest {

    private lateinit var subject: Auth0Service

    private var auth0RestTemplate: RestTemplate = mock()
    private var auth0Properties: Auth0Properties = testProperties
    private var userRepository: UserRepository = mock()

    @BeforeEach
    fun init() {
        subject = Auth0Service(
            auth0RestTemplate,
            auth0Properties,
            userRepository
        )
    }

    @Test
    fun `should login and save user`() {
        //given
        val userName = "userName"
        val password = "password"

        val request = WebAuth0LoginRequest()
            .username(userName)
            .password(password)

        val expectedUrl = auth0Properties.issuer + "/oauth/token"

        val expectedRequest = LoginRequest(
            grant_type = Auth0Service.PASSWORD_GRANT_TYPE,
            username = request.username,
            password = request.password,
            audience = auth0Properties.audience,
            client_id = auth0Properties.clientId,
            client_secret = auth0Properties.clientSecret
        )

        whenever(auth0RestTemplate.postForObject(expectedUrl, expectedRequest, LoginResponse::class.java)).thenReturn(LoginResponse(jwt_token))

        whenever(userRepository.findByUserName("auth0|642de1ff3595d0580ee31663")).thenReturn(null)

        whenever(userRepository.save(Mockito.any(User::class.java))).thenAnswer { i -> i.arguments[0] }

        //when
        subject.login(request)

        //then
        val expectedUser = User(null, "auth0|642de1ff3595d0580ee31663", mutableListOf(), mutableListOf())
        verify(userRepository).save(expectedUser)
    }

    companion object {
        val testProperties = Auth0Properties(
            "domain",
            "clientId",
            "clientSecret",
            "https://books/api",
            "https://dev-xs1u5mnzrm83ke4o.us.auth0.com/"
        )

        const val jwt_token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjRwWHRKVXlDUUxoeHU5YTBreTdDRCJ9.eyJpc3MiOiJodHRwczovL2Rldi14czF1NW1uenJtODNrZTRvLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2NDJkZTFmZjM1OTVkMDU4MGVlMzE2NjMiLCJhdWQiOiJodHRwczovL2Jvb2tzL2FwaSIsImlhdCI6MTY4NjM0NDA4MiwiZXhwIjoxNjg2NDMwNDgyLCJhenAiOiIwQ00yeTlhNXltOXVFMjBoUXZOcDJBTHI1bHBSTHIwRSIsImd0eSI6InBhc3N3b3JkIiwicGVybWlzc2lvbnMiOlsicmVhZDpub25lIl19.c-axaEKaeN6EzXJN11Q_pXyqFOjpZk0rN1zNeAC9Pv7FNChmpWi2v7oZrOdhYXoWGsatWx3DHX__oHd1ndBPlGFOqR52mXBBuXIWlJDTwWuPc-jmtQbdy3MId9tZNBmQaO9Tpw1AuLSzLTbhrNzfdoqM7_VjjJDWaHDR_XU8YKPAE8n7lNwgoEVNCKtlodzorC2a5MIb9chrTIBMyZPdJh804492_aeDzz7y1do2HT7VavqZg_N444-SwYYD_8x0MKxVvE4JGBUArykESstwiFwq4O8Lnu-mcN86-__KXf9VX7C1hUoopYzShLL2q9BYdNraKCWIzw1Nuf_jzZCE5A"
    }
}