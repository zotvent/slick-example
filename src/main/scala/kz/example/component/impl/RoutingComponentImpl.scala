package kz.example.component.impl

import akka.http.scaladsl.Http
import kz.example.component.{ActorSystemComponent, ConfigComponent, Repositories, RoutingComponent}
import kz.example.http.HttpRoute

trait RoutingComponentImpl extends RoutingComponent {
  this: ConfigComponent with ActorSystemComponent with Repositories with HttpRoute =>

  private val host = config.getString("application.host")
  private val port = config.getInt("application.port")

  Http().newServerAt(host, port).bind(route)
}
