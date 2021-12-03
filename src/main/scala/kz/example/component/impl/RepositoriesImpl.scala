package kz.example.component.impl

import kz.example.component.{ActorSystemComponent, DatabaseComponent, Repositories}
import kz.example.repository.{BooksPostgreRepository, BooksRepository}
import kz.example.utils.Logging

trait RepositoriesImpl extends Repositories {
  this: DatabaseComponent with Logging with ActorSystemComponent =>

  override val booksRepository: BooksRepository = new BooksPostgreRepository(db)
}
