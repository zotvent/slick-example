package kz.example.component.impl

import kz.example.component.DatabaseComponent
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile

trait DatabaseComponentImpl extends DatabaseComponent {
  override val db = Database.forConfig("database.postgre")
  override val profile = new PostgresProfile {}
}
