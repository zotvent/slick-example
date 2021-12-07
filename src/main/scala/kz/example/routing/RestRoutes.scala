package kz.example.routing

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import kz.example.model.repository.BookRepository
import kz.example.utils.Serializers

class RestRoutes(bookRepository: BookRepository)(implicit system: ActorSystem) extends Serializers {
  private val bookRouter = new BookRoute(bookRepository)

  val route: Route = bookRouter.route
}
