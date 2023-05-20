package com.example.books.web

import com.example.books.persistence.AuthorRepository
import com.example.books.persistence.BookRepository
import com.example.books.persistence.PublisherRepository
import com.example.books.service.DomainToWebMapper
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.generated.books.api.BooksApi
import org.generated.books.model.WebBookResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
class BookController(
    val bookRepository: BookRepository,
    val authRepository: AuthorRepository,
    val publisherRepository: PublisherRepository,
    val domainToWebMapper: DomainToWebMapper
): BooksApi {

    private val logger = KotlinLogging.logger {}

    @Override
    @Transactional
    override fun getBook(bookId: Int): ResponseEntity<WebBookResponse> {
        val book = bookRepository.findById(1).getOrNull() ?: error("Book not found")

        val response = domainToWebMapper.toWeb(book)

        return ResponseEntity.ok(response)
    }
}