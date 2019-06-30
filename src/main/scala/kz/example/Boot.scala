package kz.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import kz.example.repository.{BooksPostgreRepository, BooksRepository}
import kz.example.routing.RestRoutes
import org.slf4j.LoggerFactory
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


object Boot extends App {

  val config: Config = ConfigFactory.load()
  val actorSystemName = config.getString("akka.system.name")

  implicit val system: ActorSystem = ActorSystem(actorSystemName)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val log = LoggerFactory.getLogger(Boot.getClass)

  val db = Database.forConfig("database.postgre")
  val booksRepository: BooksRepository = new BooksPostgreRepository(db)

  booksRepository.prepareRepository().onComplete {
    case Success(_) => log.info("Books repository was successfully prepared")

    case Failure(exception) =>
      log.error("Failed to prepare books repository with exception = {}", exception.toString)
      throw exception
  }

  val restRoutes = new RestRoutes(booksRepository)

  val host = config.getString("application.host")
  val port = config.getInt("application.port")
  Http().bindAndHandle(restRoutes.route, host, port)

  log.info("{} ActorSystem started", actorSystemName)

  system.registerOnTermination {
    log.info("Terminating {} ActorSystem", actorSystemName)
  }

}
