package com.example.books.web

import org.generated.books.model.WebAuth0LoginRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/home")
    fun home(@AuthenticationPrincipal jwt: Jwt): String {
        return "home"
    }

    @GetMapping("/admin")
    fun admin(@AuthenticationPrincipal jwt: Jwt): String {
        return "admin stuff"
    }

    @PostMapping("/whatever")
    fun whatever(@AuthenticationPrincipal jwt: Jwt, input: WebAuth0LoginRequest): String {
        return "whoop"
    }
}
