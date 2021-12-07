package kz.example.model.db

import kz.example.model.entity.Book
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

object BookTableComponent {

  def apply(profile: JdbcProfile): BookTableComponent = new BookTableComponent(profile)

}

class BookTableComponent(val profile: JdbcProfile) {

  import profile.api._

  class BookTable(tag: Tag) extends Table[Book](tag, Some("book_store"), "books") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    def name: Rep[String] = column[String]("name")
    def author: Rep[String] = column[String]("author")

    def * : ProvenShape[Book] =
      (id, name, author) <> (Book.tupled, Book.unapply)

  }

  val bookQuery: TableQuery[BookTable] = TableQuery[BookTable]

}
