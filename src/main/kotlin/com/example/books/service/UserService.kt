package com.example.books.service

import com.example.books.persistence.Book
import com.example.books.persistence.BookRepository
import com.example.books.persistence.User
import com.example.books.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(val userRepository: UserRepository,
                  val bookRepository: BookRepository,
                  val authenticationService: AuthenticationService) {

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

    private fun getCurrentLoggedInUser(): User {
        val currentUserName = authenticationService.getLoggedInUserName()
        return userRepository.findByUserName(currentUserName) ?: error("user not found")
    }
}