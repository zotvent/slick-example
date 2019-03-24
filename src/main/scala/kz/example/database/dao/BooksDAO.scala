package kz.example.database.dao

import kz.example.database.model.Book
import kz.example.database.table.BooksTable
import slick.dbio.Effect
import slick.jdbc.PostgresProfile.api._
import slick.sql.FixedSqlStreamingAction


class BooksDAO extends BooksTable {

  def createSchemeIfNotExists(): DBIO[Unit] = {
    books.schema.createIfNotExists
  }

  def insert(book: Book): DBIO[Int] = {
    books += book
  }

  def update(book: Book): DBIO[Int] = {
    books.filter(_.id === book.id)
      .map(b => (b.name, b.author))
      .update((book.name, book.author))
  }

  def delete(bookId: Int): DBIO[Int] = {
    books.filter(_.id === bookId).delete
  }

  def select(bookId: Int): FixedSqlStreamingAction[Seq[Book], Book, Effect.Read] = {
    books.filter(_.id === bookId).result
  }

}
