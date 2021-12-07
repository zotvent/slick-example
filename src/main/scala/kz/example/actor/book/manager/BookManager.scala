package kz.example.actor.book.manager

import akka.actor.Props
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import kz.example.actor.PerRequestActor
import kz.example.model.entity.Book
import kz.example.model.repository.BookRepository

import scala.concurrent.Promise

object BookManager {

  def props(bookRepository: BookRepository, requestContext: RequestContext, promise: Promise[RouteResult]): Props = Props(
    new BookManager(bookRepository, requestContext, promise)
  )

  sealed trait BookRequest

  case class AddBook(book: Book) extends BookRequest
  case class GetBook(bookId: Int) extends BookRequest
  case class GetBooks(limit: Int, offset: Int) extends BookRequest
  case class UpdateBook(book: Book) extends BookRequest
  case class DeleteBook(bookId: Int) extends BookRequest

}

class BookManager(val bookRepository: BookRepository, val requestContext: RequestContext, val promise: Promise[RouteResult])
    extends PerRequestActor
    with BookAdder
    with BookGetter
    with BookUpdater
    with BookRemover {

  import BookManager._

  override def receive: Receive = { case request: BookRequest =>
    log.info("Received BookRequest = {}", request)
    handle(request)
  }

  def handle(request: BookRequest): Unit = request match {
    case s: AddBook => addBook(s)
    case s: GetBook => getBook(s)
    case s: GetBooks => getBooks(s)
    case s: UpdateBook => updateBook(s)
    case s: DeleteBook => deleteBook(s)
  }

}
