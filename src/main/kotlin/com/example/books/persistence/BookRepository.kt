package com.example.books.persistence

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface BookRepository: CrudRepository<Book, Long>  {
}

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
    var average_rating: Float,
    var isbn: String,
    var isbn13: String,
    var language_code: String,
    var num_pages: Int,
    var ratings_count: Int,
    var text_reviews_count: Int,
    var publication_date: LocalDate,
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
//    @OneToMany(targetEntity = Book::class, mappedBy = "author")
//    var books: List<Book>
)

@Entity(name = "publisher")
@Table(name = "publishers")
data class Publisher(
    @Id
    @GeneratedValue
    @Column(name = "publisher_id")
    var id: Int,
    var publisherName: String,
//    @OneToMany(targetEntity = Book::class, mappedBy = "publisher")
//    var books: List<Book>
)