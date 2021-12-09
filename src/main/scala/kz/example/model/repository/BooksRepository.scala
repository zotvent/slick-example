package kz.example.model.repository

import kz.example.model.db.BookTableComponent
import kz.example.model.db.BookTableComponent.BookId
import kz.example.model.entity.Book
import kz.example.utils.Exceptions.NotFoundException
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait BookRepositoryComponent {
  def add(book: Book): Future[Int]
  def update(book: Book): Future[Int]
  def deleteBy(bookId: Int): Future[Int]
  def byId(bookId: Int): Future[Option[Book]]
  def all(limit: Int, offset: Int): Future[Seq[Book]]
}

object BookRepository {

  def apply(db: Database, profile: JdbcProfile) =
    new BookRepository(db, profile)

  case class BookNotFoundException(id: BookId) extends NotFoundException(s"Book [id=$id] not found")
}

class BookRepository(db: Database, profile: JdbcProfile) extends BookRepositoryComponent {

  private val table = BookTableComponent(profile)

  import profile.api._
  import table.bookQuery

  override def add(book: Book): Future[Int] = db.run {
    bookQuery += book
  }

  override def update(book: Book): Future[Int] = db.run {
    bookQuery.filter(_.id === book.id).map(b => (b.name, b.author)).update((book.name, book.author))
  }

  override def deleteBy(bookId: Int): Future[Int] = db.run {
    bookQuery.filter(_.id === bookId).delete
  }

  override def byId(bookId: Int): Future[Option[Book]] = db.run {
    bookQuery.filter(_.id === bookId).result.headOption
  }

  override def all(limit: Int, offset: Int): Future[Seq[Book]] = db.run {
    bookQuery.drop(offset).take(limit).result
  }
}
