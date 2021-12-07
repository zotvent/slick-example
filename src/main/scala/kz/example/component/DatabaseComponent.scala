package kz.example.component

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.JdbcProfile

trait DatabaseComponent {
  def db: Database
  def profile: JdbcProfile
}
