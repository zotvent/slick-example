package kz.example.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.{Route, RouteResult}
import kz.example.actor.book.manager.BookManager
import kz.example.model.entity.Book
import kz.example.model.repository.BookRepository
import kz.example.utils.Serializers

import scala.concurrent.Promise

class BookRoute(bookRepository: BookRepository)(implicit system: ActorSystem) extends Serializers {

  val route: Route = pathPrefix("books") {
    concat(
      path(IntNumber) { bookId =>
        concat(
          get {
            handleBook(BookManager.GetBook(bookId))
          },
          delete {
            handleBook(BookManager.DeleteBook(bookId))
          }
        )
      },
      pathEndOrSingleSlash {
        concat(
          entity(as[Book]) { book =>
            concat(
              post {
                handleBook(BookManager.AddBook(book))
              },
              put {
                handleBook(BookManager.UpdateBook(book))
              }
            )
          },
          get {
            parameters('limit ? 20, 'offset ? 0) { (limit, offset) =>
              handleBook(BookManager.GetBooks(limit, offset))
            }
          }
        )
      }
    )
  }

  private def handleBook(request: BookManager.BookRequest): Route = ctx => {
    val p = Promise[RouteResult]
    val target = system.actorOf(BookManager.props(bookRepository, ctx, p))
    target ! request
    p.future
  }

}
