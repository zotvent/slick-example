package kz.example.component.impl

import akka.http.scaladsl.Http
import kz.example.Boot.booksRepository
import kz.example.component.{ActorSystemComponent, ConfigComponent, RoutingComponent}
import kz.example.routing.RestRoutes

trait RoutingComponentImpl extends RoutingComponent {
  this: ConfigComponent with ActorSystemComponent =>

  override val restRoutes: RestRoutes = new RestRoutes(booksRepository)
  private val host = config.getString("application.host")
  private val port = config.getInt("application.port")

  Http().bindAndHandle(restRoutes.route, host, port)
}
