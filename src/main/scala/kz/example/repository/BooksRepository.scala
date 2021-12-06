package kz.example.repository

import kz.example.model.entity.Book

import scala.concurrent.Future

trait BooksRepository {
  def add(book: Book): Future[Int]
  def update(book: Book): Future[Int]
  def deleteBook(bookId: Int): Future[Int]
  def getBook(bookId: Int): Future[Seq[Book]]
}
