import kz.example.model.Book
import kz.example.repository.{BooksPostgreRepository, BooksRepository}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable


class MyPostgresTest extends FunSuite with BeforeAndAfter with ScalaFutures {

  implicit override val patienceConfig = PatienceConfig(timeout = Span(5, Seconds))

  var db: Database = _
  var booksRepository: BooksRepository = _

  val bookId: Int = 101
  val bookForInsert = Book(bookId, "Harry Potter and the Prisoner of Azkaban", "J. K. Rowling")
  val bookForUpdate = Book(bookId, "Harry Potter and the Goblet of Fire", "J. K. Rowling")

  def insertBook: Int = booksRepository.add(bookForInsert).futureValue
  def updateBook: Int = booksRepository.update(bookForUpdate).futureValue

  before {
    db = Database.forConfig("database.postgre")
    booksRepository = new BooksPostgreRepository(db)
  }

  test("Creating the Schema works") {
    booksRepository.prepareRepository()

    val tables = db.run(MTable.getTables).futureValue
    assert(tables.count(_.name.name.equalsIgnoreCase("books")) == 1)
  }

  test("Inserting a Book works") {
    val result = insertBook
    assert(result == 1)
  }

  test("Selecting the Book works after insert operation") {
    val result = booksRepository.getBook(bookId).futureValue
    assert(result.head == bookForInsert)
  }

  test("Updating the Book works") {
    val result = updateBook
    assert(result == 1)
  }

  test("Selecting the Book works after update operation") {
    val result = booksRepository.getBook(bookId).futureValue
    assert(result.head == bookForUpdate)
  }

  test("Deleting the Book works") {
    val result = booksRepository.deleteBook(bookId).futureValue
    assert(result == 1)
  }

  test("Selecting the Book works after delete operation") {
    val result = booksRepository.getBook(bookId).futureValue
    assert(result.isEmpty)
  }

}
