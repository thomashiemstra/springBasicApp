package com.example.books.service

import com.example.books.persistence.Book
import com.example.books.persistence.BookRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Transactional
@Service
class BookService(private val bookRepository: BookRepository) {

    fun getBookById(id: Long): Book {
        return bookRepository.findById(id).getOrNull() ?: error("book not found")
    }
}