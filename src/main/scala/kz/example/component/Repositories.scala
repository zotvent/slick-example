package kz.example.component

import kz.example.model.repository.BookRepository

trait Repositories {
  def bookRepository: BookRepository
}
