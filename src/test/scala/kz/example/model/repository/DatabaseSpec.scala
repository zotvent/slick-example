package kz.example.model.repository

import kz.example.model.db.BookTableComponent
import org.scalatest.{BeforeAndAfterAll, Suite}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile

trait DatabaseSpec extends Suite with BeforeAndAfterAll {
  lazy val db = Database.forConfig("database.postgre")
  lazy val profile = new PostgresProfile {}
  lazy val bookRepository: BookRepositoryComponent = ExtendedBookRepository(db, profile)
  private val schemaName = "book_store"

  import profile.api._

  override def beforeAll(): Unit = {
    val table: BookTableComponent = bookRepository.asInstanceOf[ExtendedBookRepository].getTable
    db.run {
      DBIO.seq(sqlu"CREATE SCHEMA IF NOT EXISTS #$schemaName", table.bookQuery.schema.dropIfExists, table.bookQuery.schema.create)
    }
  }
}
