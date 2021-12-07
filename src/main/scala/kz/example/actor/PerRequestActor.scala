package kz.example.actor

import akka.actor.{Actor, ActorLogging}
import kz.example.routing.PerRequest
import kz.example.utils.Serializers

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

trait PerRequestActor extends Actor with ActorLogging with Serializers with PerRequest {

  context.setReceiveTimeout(60.seconds)

  implicit val ec: ExecutionContextExecutor = context.dispatcher

}
