package kz.example.routing

import akka.actor.Actor
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import kz.example.messages.error.ErrorCode
import kz.example.utils.Serializers

import scala.concurrent.{ExecutionContext, Promise}
import scala.util.{Success, Try}

trait PerRequest {
  this: Actor with Serializers =>

  implicit def ec: ExecutionContext

  def requestContext: RequestContext
  def promise: Promise[RouteResult]

  def complete(
      response: => ToResponseMarshallable,
      statusCode: StatusCode
  ): Unit = {
    val f = requestContext.complete(response)

    f.onComplete { res =>
      val routeResult: Try[RouteResult] = res match {
        case scala.util.Success(value: Complete) =>
          Success(
            value.copy(response = value.response.withStatus(statusCode))
          )

        case _ => res
      }
      promise.complete(routeResult)
    }

    context.stop(self)
  }

  def complete(
      response: => ToResponseMarshallable,
      errorCode: ErrorCode
  ): Unit =
    complete(response, errorCode.statusCode)

}
