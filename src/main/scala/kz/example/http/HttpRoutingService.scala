package kz.example.http

import kz.example.component.{ActorSystemComponent, Repositories}
import kz.example.http.service.BookService
import kz.example.utils.Serializers

trait HttpRoutingService extends HttpBaseService with BookService {
  this: Repositories with ActorSystemComponent with Serializers with HttpRoute =>
}
