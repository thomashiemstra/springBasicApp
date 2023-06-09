package com.example.books.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PublisherRepository: CrudRepository<Publisher, Int> {
}