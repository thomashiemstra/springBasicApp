package com.example.books.web

import com.example.books.config.IntegrationTests
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType


internal class BookControllerIntegrationTest: IntegrationTests() {

    @Test
    fun `should get a book`() {
        subject.perform(
            MockMvcRequestBuilders.get("/book/1")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(jsonPath("$.id").value(1))
    }

    @Test
    fun `should save a book`() {
        subject.perform(
            MockMvcRequestBuilders.post("/saveBook")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "title": "Some book",
                      "author": {
                        "id": "1"
                      },
                      "averageRating": "4.57",
                      "isbn": "0439785960",
                      "isbn13": "9780439785969",
                      "language_code": "eng",
                      "num_pages": "1",
                      "ratings_count": "1",
                      "text_reviews_count": "1",
                      "publication_date": "2006-09-16",
                      "publisher": {
                        "id": "1"
                      }
                    }
                    """.trimIndent()
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

}