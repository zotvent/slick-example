package kz.example.component

import kz.example.model.repository.BookRepositoryComponent

trait Repositories {
  def bookRepository: BookRepositoryComponent
}
