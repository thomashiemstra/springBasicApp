package com.example.books.service

import com.example.books.defaultBook
import com.example.books.defaultUser
import com.example.books.persistence.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*


internal class UserServiceTest {
    private val userRepository: UserRepository = mock()
    private val bookRepository: BookRepository = mock()
    private val authenticationService: AuthenticationService = mock()
    private val bookListRepository: BookListRepository = mock()
    private val mapper: Mapper = MapperImpl()

    private lateinit var subject: UserService



    @BeforeEach
    fun init() {
        subject = UserService(
            userRepository,
            bookRepository,
            authenticationService,
            bookListRepository,
            mapper
        )
    }

    @Test
    fun `should add book as favourite`() {
        //given
        val bookId = 1
        val book = defaultBook(id = bookId)

        whenever(bookRepository.findById(1)).thenReturn(
            Optional.of(book)
        )

        val user = mockUser()

        //when
        subject.addFavouriteBook(bookId)

        //then
        assertThat(user.favouriteBooks).contains(book)
    }

    @Test
    fun `should not add book again`() {
        //given
        val bookId = 1
        val book = defaultBook(id = bookId)

        val secondBook = defaultBook(id = 2)

        whenever(bookRepository.findById(1)).thenReturn(
            Optional.of(book)
        )

        val user = mockUser(favouriteBooks = mutableListOf(book, secondBook))

        //when
        subject.addFavouriteBook(bookId)

        //then
        assertThat(user.favouriteBooks.size).isEqualTo(2)
    }

    private fun mockUser(favouriteBooks: MutableList<Book> = mutableListOf()): User {
        whenever(authenticationService.getLoggedInUserName()).thenReturn(USER_NAME)

        val user = defaultUser(favouriteBooks = favouriteBooks)
        whenever(userRepository.findByUserName(USER_NAME)).thenReturn(user)
        return user
    }

    companion object {
        const val USER_NAME = "someone";
    }
}