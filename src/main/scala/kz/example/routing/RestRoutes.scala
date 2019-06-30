package kz.example.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import kz.example.repository.BooksRepository
import kz.example.utils.Serializers


class RestRoutes(booksRepository: BooksRepository)
                (implicit system: ActorSystem)
  extends Serializers {

  private val bookRouter = new BookRoute(booksRepository)

  val route: Route = bookRouter.route

}
