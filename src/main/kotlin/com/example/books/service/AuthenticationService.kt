package com.example.books.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationService {

    fun getLoggedInUserName() = SecurityContextHolder.getContext().authentication.name
}