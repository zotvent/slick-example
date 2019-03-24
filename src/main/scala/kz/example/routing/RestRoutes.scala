package kz.example.routing

import akka.http.scaladsl.server.Route
import kz.example.database.BookService
import kz.example.utils.Serializers

import scala.concurrent.ExecutionContext


class RestRoutes(bookService: BookService)
                (implicit executionContext: ExecutionContext)
  extends Serializers {

  private val bookRouter = new BookRoute(bookService)

  val route: Route = bookRouter.route

}
