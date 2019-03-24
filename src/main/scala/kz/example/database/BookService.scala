package kz.example.database

import kz.example.database.dao.BooksDAO
import kz.example.database.model.Book
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future


trait BookService {

  def insert(book: Book): Future[Int]
  def update(book: Book): Future[Int]
  def delete(bookId: Int): Future[Int]
  def select(bookId: Int): Future[Seq[Book]]

}


class BookServiceImpl(db: Database, booksDAO: BooksDAO) extends BookService {

  def createSchemeIfNotExists(): Future[Unit] = {
    db.run(booksDAO.createSchemeIfNotExists())
  }

  override def insert(book: Book): Future[Int] = {
    db.run(booksDAO.insert(book))
  }

  override def update(book: Book): Future[Int] = {
    db.run(booksDAO.update(book))
  }

  override def delete(bookId: Int): Future[Int] = {
    db.run(booksDAO.delete(bookId))
  }

  override def select(bookId: Int): Future[Seq[Book]] = {
    db.run(booksDAO.select(bookId))
  }

}
