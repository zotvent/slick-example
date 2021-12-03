package kz.example.messages.error

import akka.http.scaladsl.model.StatusCode

trait ErrorCode {
  def statusCode: StatusCode
}
