package com.example.books.web

import com.example.books.service.Mapper
import com.example.books.service.UserService
import org.generated.books.api.UsersApi
import org.generated.books.model.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService,
                     val mapper: Mapper): UsersApi {
    override fun addFavouriteBook(webFavouriteRequest: WebFavouriteRequest): ResponseEntity<Void> {
        userService.addFavouriteBook(webFavouriteRequest.id)
        return ResponseEntity.ok(null)
    }

    override fun removeFavouriteBook(webFavouriteRequest: WebFavouriteRequest): ResponseEntity<Void> {
        userService.removeFavouriteBook(webFavouriteRequest.id)
        return ResponseEntity.ok(null)
    }

    override fun getFavourites(): ResponseEntity<List<WebBook>> {
        val books = userService.getFavouriteBooks()

        val webBooks = mapper.toWebBooks(books)

        return ResponseEntity.ok(webBooks)
    }

    override fun createBookList(webBookListRequest: WebBookListRequest): ResponseEntity<Void> {
        userService.createBookList(webBookListRequest.bookListName)
        return ResponseEntity.ok(null)
    }

    override fun removeBookList(webBookListRequest: WebBookListRequest): ResponseEntity<Void> {
        userService.removeBookList(webBookListRequest.bookListName)
        return ResponseEntity.ok(null)
    }

    override fun addBookToBookList(webAddBookToBookListRequest: WebBookToBookListRequest): ResponseEntity<Void> {
        userService.addBookToBookList(webAddBookToBookListRequest.bookListName, webAddBookToBookListRequest.bookId)
        return ResponseEntity.ok(null)
    }

    override fun getBookLists(): ResponseEntity<List<WebBookListResponse>> {
        val webBookLists = userService.getAllBookLists()
        return ResponseEntity.ok(webBookLists)
    }

    override fun removeBookFromBookList(webBookToBookListRequest: WebBookToBookListRequest): ResponseEntity<Void> {
        userService.removeBookFromBookList(webBookToBookListRequest.bookListName, webBookToBookListRequest.bookId)
        return ResponseEntity.ok(null)
    }
}