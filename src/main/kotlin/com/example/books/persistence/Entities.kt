package com.example.books.persistence

import jakarta.persistence.*
import net.minidev.json.annotate.JsonIgnore
import java.time.LocalDate


@Entity(name = "book")
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    var id: Int?,
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    var id: Int?,
    var authorName: String,
    @JsonIgnore
    @OneToMany(targetEntity = Book::class, mappedBy = "author")
    var books: List<Book>?
)

@Entity(name = "publisher")
@Table(name = "publishers")
data class Publisher(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    var id: Int?,
    var publisherName: String,
    @JsonIgnore
    @OneToMany(targetEntity = Book::class, mappedBy = "publisher")
    var books: List<Book>?
)

@Entity(name = "user")
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Int?,
    var userName: String,
    @OneToMany
    @JoinTable(name = "favourite_books_join_table",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id", referencedColumnName = "book_id")])
    var favouriteBooks: MutableList<Book>,
    @OneToMany(targetEntity = BookList::class, mappedBy = "user")
    var bookLists: MutableList<BookList>
)

@Entity(name = "bookList")
@Table(name = "book_lists")
data class BookList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_list_id")
    var id: Int?,
    var bookListName: String,
    @ManyToMany
    @JoinTable(name = "book_list_join_table",
        joinColumns = [JoinColumn(name = "book_list_id", referencedColumnName = "book_list_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id", referencedColumnName = "book_id")])
    var books: MutableList<Book>,
    @ManyToOne
    @JoinColumn(name="user_id")
    var user: User
)