package kz.example.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.{Route, RouteResult}
import kz.example.actor.book.manager.BookManager
import kz.example.model.Book
import kz.example.repository.BooksRepository
import kz.example.utils.Serializers

import scala.concurrent.Promise

class BookRoute(booksRepository: BooksRepository)(implicit system: ActorSystem)
    extends Serializers {

  val route: Route = pathPrefix("books") {
    path(IntNumber) { bookId =>
      get {
        handleBook(BookManager.GetBook(bookId))
      } ~
        delete {
          handleBook(BookManager.DeleteBook(bookId))
        }
    } ~
      pathEndOrSingleSlash {
        entity(as[Book]) { book =>
          post {
            handleBook(BookManager.AddBook(book))
          } ~
            put {
              handleBook(BookManager.UpdateBook(book))
            }
        }
      }
  }

  private def handleBook(request: BookManager.BookRequest): Route = ctx => {
    val p = Promise[RouteResult]
    val target = system.actorOf(BookManager.props(booksRepository, ctx, p))
    target ! request
    p.future
  }

}
