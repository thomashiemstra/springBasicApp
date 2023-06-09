package com.example.books.web

import com.example.books.persistence.Book
import com.example.books.persistence.BookRepository
import jakarta.transaction.Transactional
import org.generated.books.model.WebAuth0LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class HomeController(val bookRepository: BookRepository) {

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
