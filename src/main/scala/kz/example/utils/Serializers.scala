package kz.example.utils

import kz.example.database.model.Book
import org.json4s.{ShortTypeHints, jackson}
import org.json4s.native.Serialization
import de.heikoseeberger.akkahttpjson4s.Json4sSupport


trait Serializers extends Json4sSupport {

  implicit val serialization = jackson.Serialization

  implicit val formats = Serialization.formats(
    ShortTypeHints(
      List(
        classOf[Book]
      )
    )
  )

}
