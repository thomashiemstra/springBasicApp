package com.example.books.service

import com.example.books.persistence.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Transactional
@Service
class BookService(private val bookRepository: BookRepository,
                  private val authorRepository: AuthorRepository,
                  private val publisherRepository: PublisherRepository
) {

    fun getBookById(id: Int): Book {
        return bookRepository.findById(id).getOrNull() ?: error("book not found")
    }

    fun saveBook(book: Book) {
        book.author = authorRepository.findById(book.author.id!!).getOrNull() ?: error("invalid author id provided")
        book.publisher = publisherRepository.findById(book.publisher.id!!).getOrNull() ?: error("invalid publisher id provided")

        bookRepository.save(book)
    }
}