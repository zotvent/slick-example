package kz.example.component.impl

import kz.example.component.{ActorSystemComponent, DatabaseComponent, Repositories}
import kz.example.repository.{BooksPostgreRepository, BooksRepository}
import kz.example.utils.Logging

import scala.util.{Failure, Success}

trait RepositoriesImpl extends Repositories {
  this: DatabaseComponent with Logging with ActorSystemComponent =>

  override val booksRepository: BooksRepository = new BooksPostgreRepository(db)

  booksRepository.prepareRepository().onComplete {
    case Success(_) =>
      log.info("Books repository was successfully prepared")
    case Failure(exception) =>
      log.error("Failed to prepare books repository with exception = {}", exception.toString)
      throw exception
  }
}
