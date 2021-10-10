package kz.example.component

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait ActorSystemComponent {
  implicit def actorSystem: ActorSystem
  implicit def materializer: ActorMaterializer
  implicit def executionContext: ExecutionContext
}
