package com.example.books.persistence

import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<Book, Long>  {
}
