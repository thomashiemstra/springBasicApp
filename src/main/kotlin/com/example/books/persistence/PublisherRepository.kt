package com.example.books.persistence

import org.springframework.data.repository.CrudRepository

interface PublisherRepository: CrudRepository<Publisher, Long> {
}