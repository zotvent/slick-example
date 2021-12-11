package kz.example.http

import akka.http.scaladsl.server.Route

trait HttpRoute {
  def route: Route
}
