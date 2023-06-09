package com.example.books.service

import com.example.books.persistence.Author
import com.example.books.persistence.Book
import com.example.books.persistence.Publisher
import org.generated.books.model.WebAuthor
import org.generated.books.model.WebBook
import org.generated.books.model.WebPublisher
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface Mapper  {
    fun toWebBook(book: Book): WebBook

    @Mapping(target = "author.books", ignore = true)
    @Mapping(target = "publisher.books", ignore = true)
    fun toDomainBook(book: WebBook): Book

    fun toWebAuthor(author: Author): WebAuthor

    fun toWebPublisher(publisher: Publisher): WebPublisher
    fun toWebBooks(books: List<Book>): List<WebBook>
}