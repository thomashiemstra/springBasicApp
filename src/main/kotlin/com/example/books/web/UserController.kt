package com.example.books.web

import com.example.books.service.Mapper
import com.example.books.service.UserService
import org.generated.books.api.UsersApi
import org.generated.books.model.WebBook
import org.generated.books.model.WebFavouriteRequest
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

}