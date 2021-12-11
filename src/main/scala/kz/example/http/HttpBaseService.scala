package kz.example.http

import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.collection.mutable
import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.util.{Failure, Success}

trait HttpBaseService {
  this: HttpRoute =>

  private val routes = mutable.MutableList[Route]()

  override lazy val route: Route = {
    if (routes.nonEmpty) {
      routes.reduceLeft((next, prev) => next ~ prev)
    } else {
      pathPrefix(RemainingPath) { _ =>
        complete(StatusCodes.NotImplemented)
      }
    }
  }

  protected def registerRoute(route: Route): Unit = routes += route

  protected def defaultStatusCode[T]: T => StatusCode = _ => StatusCodes.OK

  protected def handleResponse[T: ClassTag](future: Future[_], statusCode: T => StatusCode = defaultStatusCode)(implicit
      m: ToEntityMarshaller[T]
  ): Route =
    onComplete(future.mapTo[T]) {
      case Success(value) => complete(statusCode(value), value)
      case Failure(exception) => complete(StatusCodes.InternalServerError, exception.getMessage)
    }
}
