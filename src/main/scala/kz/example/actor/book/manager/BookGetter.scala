package kz.example.actor.book.manager

import akka.http.scaladsl.model.StatusCodes
import kz.example.actor.PerRequestActor
import kz.example.messages.error.ErrorMessages
import kz.example.model.entity.Book
import kz.example.model.repository.BookRepository

import scala.util.{Failure, Success}

trait BookGetter {
  this: PerRequestActor =>

  import BookManager.GetBook

  def bookRepository: BookRepository

  def getBook(request: GetBook): Unit =
    bookRepository.byId(request.bookId).onComplete {
      case Success(value) =>
        log.debug("Successfully got book")
        prepareResponse(value)

      case Failure(exception) =>
        log.error("Got exception while getting book = {}", exception.toString)
        complete(ErrorMessages.INTERNAL_SERVER_ERROR, StatusCodes.InternalServerError)
    }

  private def prepareResponse(book: Option[Book]): Unit =
    if (book.isEmpty) {
      complete(ErrorMessages.BOOK_NOT_FOUND, StatusCodes.NotFound)
    } else {
      complete(book.get, StatusCodes.OK)
    }

}
