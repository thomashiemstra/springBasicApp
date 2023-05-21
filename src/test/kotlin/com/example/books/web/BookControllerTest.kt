package com.example.books.web

import com.example.books.config.IntegrationTests
import org.junit.jupiter.api.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType


internal class BookControllerTest: IntegrationTests() {

    @Test
    fun whatever() {
        subject.perform(
            MockMvcRequestBuilders.get("/book/1")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}