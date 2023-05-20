package com.example.books.persistence

import org.springframework.data.repository.CrudRepository

interface AuthorRepository: CrudRepository<Author, Long> {
}