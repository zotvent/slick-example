package kz.example.utils

object Exceptions {
  class NotFoundException(message: String) extends NoSuchElementException(message)
}
