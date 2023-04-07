package com.example.books.web

import com.example.books.service.Auth0Service
import org.generated.books.api.LoginApi
import org.generated.books.model.WebAuth0LoginRequest
import org.generated.books.model.WebAuth0LoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth0")
class Auth0Controller(val auth0Service: Auth0Service): LoginApi {

    override fun authenticate(webAuth0LoginRequest: WebAuth0LoginRequest): ResponseEntity<WebAuth0LoginResponse> {
        return ResponseEntity.ok(auth0Service.login(webAuth0LoginRequest))
    }
}