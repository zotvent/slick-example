package kz.example.component.impl

import kz.example.component.{ActorSystemComponent, DatabaseComponent, Repositories}
import kz.example.model.repository.BookRepository
import kz.example.utils.Logging

trait RepositoriesImpl extends Repositories {
  this: DatabaseComponent with Logging with ActorSystemComponent =>

  override val bookRepository: BookRepository = new BookRepository(db, profile)
}
