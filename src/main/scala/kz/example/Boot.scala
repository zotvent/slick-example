package kz.example

import kz.example.component.impl._
import kz.example.utils.Logging

object Boot
    extends App
    with Logging
    with ConfigComponentImpl
    with ActorSystemComponentImpl
    with DatabaseComponentImpl
    with RepositoriesImpl
    with RoutingComponentImpl
