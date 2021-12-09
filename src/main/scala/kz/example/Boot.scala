package kz.example

import kz.example.component.impl._
import kz.example.http.{HttpRoute, HttpRoutingService}
import kz.example.utils.{Logging, Serializers}

object Boot
    extends App
    with Logging
    with ConfigComponentImpl
    with ActorSystemComponentImpl
    with DatabaseComponentImpl
    with RepositoriesImpl
    with Serializers
    with HttpRoute
    with HttpRoutingService
    with RoutingComponentImpl
