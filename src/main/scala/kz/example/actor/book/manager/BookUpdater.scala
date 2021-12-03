package kz.example.actor.book.manager

import akka.http.scaladsl.model.StatusCodes
import kz.example.actor.PerRequestActor
import kz.example.messages.error.ErrorMessages
import kz.example.model.Book
import kz.example.repository.BooksRepository

import scala.util.{Failure, Success}

trait BookUpdater {
  this: PerRequestActor =>

  import BookManager.UpdateBook

  def booksRepository: BooksRepository

  def updateBook(request: UpdateBook): Unit =
    booksRepository.update(request.book).onComplete {
      case Success(value) =>
        log.debug("Successfully updated book")
        prepareResponse(value, request.book)

      case Failure(exception) =>
        log.error("Got exception while removing book = {}", exception.toString)
        complete(
          ErrorMessages.INTERNAL_SERVER_ERROR,
          StatusCodes.InternalServerError
        )
    }

  private def prepareResponse(response: Int, book: Book): Unit =
    response match {
      case 0 => complete(ErrorMessages.BOOK_NOT_FOUND, StatusCodes.NotFound)
      case 1 => complete(book, StatusCodes.OK)
      case _ =>
        complete(
          ErrorMessages.INTERNAL_SERVER_ERROR,
          StatusCodes.InternalServerError
        )
    }

}
