package kz.example.repository

import kz.example.model.Book
import kz.example.database.table.BooksTable
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future


class BooksPostgreRepository(db: Database)
  extends BooksRepository
    with BooksTable {

  override def add(book: Book): Future[Int] = {
    db.run(
      books += book
    )
  }

  override def update(book: Book): Future[Int] = {
    db.run(
      books.filter(_.id === book.id)
        .map(b => (b.name, b.author))
        .update((book.name, book.author))
    )
  }

  override def deleteBook(bookId: Int): Future[Int] = {
    db.run(
      books.filter(_.id === bookId).delete
    )
  }

  override def getBook(bookId: Int): Future[Seq[Book]] = {
    db.run(
      books.filter(_.id === bookId).result
    )
  }

  override def prepareRepository(): Future[Unit] = {
    db.run(
      books.schema.createIfNotExists
    )
  }

}
