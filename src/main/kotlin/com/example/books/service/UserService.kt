package com.example.books.service

import com.example.books.persistence.*
import jakarta.transaction.Transactional
import org.generated.books.model.WebBookListResponse
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(val userRepository: UserRepository,
                  val bookRepository: BookRepository,
                  val authenticationService: AuthenticationService,
                  val bookListRepository: BookListRepository,
                  val mapper: Mapper) {

    @Transactional
    fun addFavouriteBook(id: Int) {
        val bookToAdd = bookRepository.findById(id).getOrNull() ?: error("book not found")
        val user = getCurrentLoggedInUser()

        val favourites = user.favouriteBooks
        if (favourites.contains(bookToAdd)) return

        favourites.add(bookToAdd)
    }

    @Transactional
    fun removeFavouriteBook(id: Int) {
        val bookToRemove = bookRepository.findById(id).getOrNull() ?: error("book not found")
        val user = getCurrentLoggedInUser()

        user.favouriteBooks.remove(bookToRemove)
    }

    @Transactional
    fun getFavouriteBooks(): List<Book> {
        val user = getCurrentLoggedInUser()
        return user.favouriteBooks.toList()
    }

    @Transactional
    fun createBookList(bookListName: String) {
        val user = getCurrentLoggedInUser()

        val bookLists = user.bookLists
        if (bookLists.find { list -> list.bookListName == bookListName } != null) return

        bookListRepository.save(BookList(null, bookListName, mutableListOf(), user))
    }

    @Transactional
    fun removeBookList(bookListName: String) {
        val user = getCurrentLoggedInUser()

        val bookList = user.bookLists.first { list -> list.bookListName == bookListName }

        bookListRepository.delete(bookList)
    }

    @Transactional
    fun addBookToBookList(bookListName: String, bookId: Int) {
        val user = getCurrentLoggedInUser()
        val bookList = user.bookLists.find { list -> list.bookListName == bookListName } ?: error("non existent list")

        val book = bookRepository.findById(bookId).getOrNull() ?: error("book not found")

        bookList.books.add(book)
    }

    @Transactional
    fun getAllBookLists(): List<WebBookListResponse> {
        val user = getCurrentLoggedInUser()
        val bookLists = user.bookLists
        return mapper.toWebBookLists(bookLists)
    }

    private fun getCurrentLoggedInUser(): User {
        val currentUserName = authenticationService.getLoggedInUserName()
        return userRepository.findByUserName(currentUserName) ?: error("user not found")
    }

    @Transactional
    fun removeBookFromBookList(bookListName: String, bookId: Int) {
        val bookList = bookListRepository.findByBookListName(bookListName) ?: error("bookList not found")
        val bookToRemove = bookRepository.findById(bookId).getOrNull() ?: error("invalid book to remove")
        bookList.books.remove(bookToRemove)
    }
}