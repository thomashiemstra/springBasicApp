package com.example.books.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookListRepository: CrudRepository<BookList, Int> {

    fun findByBookListName(bookListName: String): BookList?
}