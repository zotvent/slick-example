package kz.example.routing

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import kz.example.database.BookService
import kz.example.database.model.Book
import kz.example.utils.Serializers

import scala.concurrent.ExecutionContext


class BookRoute(bookService: BookService)
               (implicit executionContext: ExecutionContext)
  extends Serializers {

  import StatusCodes._

  val route: Route = pathPrefix("books") {
    path(IntNumber) { bookId =>
      get {
        complete(bookService.select(bookId).map {
          case books: Seq[Book] => {
            if (books.isEmpty) {
              NotFound -> s"There is no book with id = $bookId"
            } else {
              OK -> books
            }
          }

          case any => {
            InternalServerError -> any.toString
          }
        })
      } ~
      delete {
        complete(bookService.delete(bookId).map {
          case result: Int if result == 1 => {
            OK -> s"Book with id = $bookId was deleted"
          }

          case result: Int if result == 0 => {
            NotFound -> s"There is no book with id = $bookId"
          }

          case any => {
            InternalServerError -> any.toString
          }
        })
      }
    } ~
    pathEndOrSingleSlash {
      entity(as[Book]) { book =>
        post {
          complete(bookService.insert(book).map {
            case result: Int if result == 1 => {
              Created -> s"Book with id = ${book.id} was inserted"
            }

            case any => {
              InternalServerError -> any.toString
            }
          })
        } ~
        put {
          complete(bookService.update(book).map {
            case result: Int if result == 1 => {
              OK -> s"Book with id = ${book.id} was updated"
            }

            case result: Int if result == 0 => {
              NotFound -> s"There is no book with id = ${book.id}"
            }

            case any => {
              InternalServerError -> any.toString
            }
          })
        }
      }
    }
  }

}
