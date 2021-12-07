package kz.example.actor.book.manager

import akka.http.scaladsl.model.StatusCodes
import kz.example.actor.PerRequestActor
import kz.example.messages.error.ErrorMessages
import kz.example.model.repository.BookRepository

import scala.util.{Failure, Success}

trait BookAdder {
  this: PerRequestActor =>

  import BookManager.AddBook

  def bookRepository: BookRepository

  def addBook(request: AddBook): Unit =
    bookRepository.add(request.book).onComplete {
      case Success(_) =>
        log.debug("Successfully added book")
        complete(request.book, StatusCodes.Created)

      case Failure(exception) =>
        log.error("Got exception while adding book = {}", exception.toString)
        complete(ErrorMessages.INTERNAL_SERVER_ERROR, StatusCodes.InternalServerError)
    }

}
