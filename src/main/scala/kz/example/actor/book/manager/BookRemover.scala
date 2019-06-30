package kz.example.actor.book.manager

import akka.http.scaladsl.model.StatusCodes
import kz.example.actor.PerRequestActor
import kz.example.messages.error.ErrorMessages
import kz.example.messages.success.SuccessMessages
import kz.example.repository.BooksRepository

import scala.util.{Failure, Success}


trait BookRemover {
  this: PerRequestActor =>

  import BookManager.DeleteBook

  def booksRepository: BooksRepository

  def deleteBook(request: DeleteBook): Unit = {
    booksRepository.deleteBook(request.bookId).onComplete {
      case Success(value) =>
        log.debug("Successfully removed book")
        prepareResponse(value)

      case Failure(exception) =>
        log.error("Got exception while removing book = {}", exception.toString)
        complete(ErrorMessages.INTERNAL_SERVER_ERROR, StatusCodes.InternalServerError)
    }
  }

  private def prepareResponse(response: Int): Unit = response match {
    case 0 => complete(ErrorMessages.BOOK_NOT_FOUND, StatusCodes.NotFound)
    case 1 => complete(SuccessMessages.BOOK_DELETED, StatusCodes.OK)
    case _ => complete(ErrorMessages.INTERNAL_SERVER_ERROR, StatusCodes.InternalServerError)
  }

}
