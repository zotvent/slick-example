package kz.example.component

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait ActorSystemComponent {
  implicit def actorSystem: ActorSystem
  implicit def executionContext: ExecutionContext
}
