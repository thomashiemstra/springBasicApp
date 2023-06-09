package com.example.books.web

import com.example.books.persistence.service.BookService
import com.example.books.persistence.service.Mapper
import mu.KotlinLogging
import org.generated.books.api.BooksApi
import org.generated.books.model.WebBook
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    val bookService: BookService,
    val mapper: Mapper,
): BooksApi {

    private val logger = KotlinLogging.logger {}

    override fun getBook(bookId: Int): ResponseEntity<WebBook> {
        val book = bookService.getBookById(bookId.toLong())
        val response = mapper.toWebBook(book)
        return ResponseEntity.ok(response)
    }

    override fun saveBook(webBook: WebBook): ResponseEntity<Void> {
        val book = mapper.toDomainBook(webBook)
        bookService.saveBook(book)
        return ResponseEntity.ok(null)
    }
}