package kz.example.database.table

import kz.example.model.entity.Book
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

trait BooksTable {

  class Books(tag: Tag) extends Table[Book](tag, Some("book_store"), "books") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    def name: Rep[String] = column[String]("name")
    def author: Rep[String] = column[String]("author")

    def * : ProvenShape[Book] =
      (id, name, author) <> (Book.tupled, Book.unapply)

  }

  val books = TableQuery[Books]

}
