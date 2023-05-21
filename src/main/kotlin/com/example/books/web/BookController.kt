package com.example.books.web

import com.example.books.service.BookService
import com.example.books.service.DomainToWebMapper
import mu.KotlinLogging
import org.generated.books.api.BooksApi
import org.generated.books.model.WebBookResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    val bookService: BookService,
    val domainToWebMapper: DomainToWebMapper
): BooksApi {

    private val logger = KotlinLogging.logger {}

    override fun getBook(bookId: Int): ResponseEntity<WebBookResponse> {
        val book = bookService.getBookById(bookId.toLong())
        val response = domainToWebMapper.toWeb(book)
        return ResponseEntity.ok(response)
    }
}