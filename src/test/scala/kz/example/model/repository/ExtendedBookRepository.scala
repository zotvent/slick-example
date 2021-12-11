package kz.example.model.repository

import kz.example.model.db.BookTableComponent
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile

object ExtendedBookRepository {

  def apply(db: Database, profile: JdbcProfile) =
    new ExtendedBookRepository(db, profile)
}

class ExtendedBookRepository(db: Database, profile: JdbcProfile) extends BookRepository(db, profile) {
  def getTable: BookTableComponent = table
}
