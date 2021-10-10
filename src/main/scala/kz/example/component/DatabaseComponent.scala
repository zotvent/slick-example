package kz.example.component

import slick.jdbc.JdbcBackend.Database

trait DatabaseComponent {
  def db: Database
}
