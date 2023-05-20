package com.example.books.service

import com.example.books.persistence.Book
import org.generated.books.model.WebBookResponse
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface DomainToWebMapper  {
    fun toWeb(book: Book): WebBookResponse
}