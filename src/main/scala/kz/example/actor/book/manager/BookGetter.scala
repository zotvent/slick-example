package kz.example.actor.book.manager

import akka.http.scaladsl.model.StatusCodes
import kz.example.actor.PerRequestActor
import kz.example.messages.error.ErrorMessages
import kz.example.model.Book
import kz.example.repository.BooksRepository

import scala.util.{Failure, Success}


trait BookGetter {
  this: PerRequestActor =>

  import BookManager.GetBook

  def booksRepository: BooksRepository

  def getBook(request: GetBook): Unit = {
    booksRepository.getBook(request.bookId).onComplete {
      case Success(value) =>
        log.debug("Successfully got book")
        prepareResponse(value)

      case Failure(exception) =>
        log.error("Got exception while getting book = {}", exception.toString)
        complete(ErrorMessages.INTERNAL_SERVER_ERROR, StatusCodes.InternalServerError)
    }
  }

  private def prepareResponse(books: Seq[Book]): Unit = {
    if (books.isEmpty) {
      complete(ErrorMessages.BOOK_NOT_FOUND, StatusCodes.NotFound)
    } else {
      complete(books.head, StatusCodes.OK)
    }
  }

}
