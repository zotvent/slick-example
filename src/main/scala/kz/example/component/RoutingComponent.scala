package kz.example.component

import akka.http.scaladsl.server.Route
import kz.example.routing.RestRoutes

trait RoutingComponent {
  def restRoutes: RestRoutes
}
