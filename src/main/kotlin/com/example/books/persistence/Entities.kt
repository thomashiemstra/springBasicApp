package com.example.books.persistence

import jakarta.persistence.*
import net.minidev.json.annotate.JsonIgnore
import java.time.LocalDate


@Entity(name = "book")
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "book_id")
    var id: Int,
    var title: String,
    @ManyToOne
    @JoinColumn(name="author_id")
    var author: Author,
    var averageRating: Float,
    var isbn: String,
    var isbn13: String,
    var languageCode: String,
    var numPages: Int,
    var ratingsCount: Int,
    var textReviewsCount: Int,
    var publicationDate: LocalDate,
    @ManyToOne
    @JoinColumn(name="publisher_id")
    var publisher: Publisher,
)

@Entity(name = "author")
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue
    @Column(name = "author_id")
    var id: Int,
    var authorName: String,
    @JsonIgnore
    @OneToMany(targetEntity = Book::class, mappedBy = "author")
    var books: List<Book>
)

@Entity(name = "publisher")
@Table(name = "publishers")
data class Publisher(
    @Id
    @GeneratedValue
    @Column(name = "publisher_id")
    var id: Int,
    var publisherName: String,
    @JsonIgnore
    @OneToMany(targetEntity = Book::class, mappedBy = "publisher")
    var books: List<Book>
)