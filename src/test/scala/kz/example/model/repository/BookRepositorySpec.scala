package kz.example.model.repository

import kz.example.model.entity.Book
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class BookRepositorySpec extends AsyncFreeSpec with DatabaseSpec with Matchers {

  "BookRepositorySpec" - {
    val book = Book(1, "name", "author")
    val updatedBook = book.copy(name = "updated name", author = "updated author")

    "should add book successfully" in {
      bookRepository.add(book).map { result =>
        result shouldEqual 1
      }
    }

    "should get the created book" in {
      bookRepository.byId(book.id).map { result =>
        result.isDefined shouldEqual true
        result.get shouldEqual book
      }
    }

    "should update the book" in {
      bookRepository.update(updatedBook).map { result =>
        result shouldEqual 1
      }
    }

    "should get the updated book" in {
      bookRepository.byId(book.id).map { result =>
        result.isDefined shouldEqual true
        result.get shouldEqual updatedBook
      }
    }

    "should delete the book" in {
      bookRepository.deleteBy(book.id).map { result =>
        result shouldEqual 1
      }
    }

    "shouldn't get the deleted book" in {
      bookRepository.byId(book.id).map { result =>
        result shouldEqual None
      }
    }
  }
}
