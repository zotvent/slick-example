package kz.example.http.service

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import kz.example.component.{ActorSystemComponent, Repositories}
import kz.example.http.HttpBaseService
import kz.example.model.entity.Book
import kz.example.model.repository.BookRepository.BookNotFoundException
import kz.example.utils.Serializers

trait BookService {
  this: Repositories with HttpBaseService with ActorSystemComponent with Serializers =>

  registerRoute(pathPrefix("books") {
    concat(
      path(IntNumber) { bookId =>
        concat(
          get {
            handleResponse[Book](bookRepository.byId(bookId).map(_.getOrElse(throw BookNotFoundException(bookId))))
          },
          delete {
            handleResponse[String](bookRepository.deleteBy(bookId).map(_ => s"Book [id=$bookId] was deleted successfully"))
          }
        )
      },
      pathEndOrSingleSlash {
        concat(
          entity(as[Book]) { book =>
            concat(
              post {
                handleResponse[Book](bookRepository.add(book).map(_ => book), _ => StatusCodes.Created)
              },
              put {
                handleResponse[Book](bookRepository.update(book).map(_ => book))
              }
            )
          },
          get {
            parameters('limit ? 20, 'offset ? 0) { (limit, offset) =>
              handleResponse[Seq[Book]](bookRepository.all(limit, offset))
            }
          }
        )
      }
    )
  })

}
