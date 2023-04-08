package com.example.books.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Long>  {
}

@Entity(name = "book")
data class Book(
    @Id
    @GeneratedValue(generator = "uuid")
    var id: String,
    var name: String
)