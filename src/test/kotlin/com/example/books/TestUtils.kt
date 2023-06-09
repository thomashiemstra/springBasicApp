package com.example.books

import com.example.books.persistence.*
import java.time.LocalDate


fun defaultBook(
    id: Int? = 1,
    title: String = "title",
    author: Author = defaultAuthor(),
    averageRating: Float = 100.0F,
    isbn: String = "0439785960",
    isbn13: String = "9780439785969",
    languageCode: String = "NL",
    numPages: Int = 420,
    ratingsCount: Int = 69,
    textReviewsCount: Int = 10,
    publicationDate: LocalDate = LocalDate.now(),
    publisher: Publisher = defaultPublisher(),
) = Book(
        id,
        title,
        author,
        averageRating,
        isbn,
        isbn13,
        languageCode,
        numPages,
        ratingsCount,
        textReviewsCount,
        publicationDate,
        publisher
    )


fun defaultAuthor(id: Int? = 1, authorName: String = "someone") = Author(id, authorName, null)

fun defaultPublisher(id: Int? = 1, publisherName: String = "some publisher") = Publisher(id, publisherName, null)

fun defaultUser(id: Int? = 1,
                userName: String = "UserName",
                favouriteBooks: MutableList<Book> = mutableListOf(),
                bookLists: MutableList<BookList> = mutableListOf()): User {
    return User(id, userName, favouriteBooks ,bookLists)
}


