package kz.example.model.entity

import kz.example.model.db.BookTableComponent.BookId

case class Book(id: BookId, name: String, author: String)
