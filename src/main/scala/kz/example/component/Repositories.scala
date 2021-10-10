package kz.example.component

import kz.example.repository.BooksRepository

trait Repositories {
  def booksRepository: BooksRepository
}
