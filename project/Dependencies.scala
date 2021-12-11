import sbt._

object Version {
  val akka = "2.6.17"
  val slick = "3.3.3"
  val postgres = "42.3.1"
  val akkaHttp = "10.2.7"
  val json4s = "4.0.3"
  val akkaHttpJson4s = "1.38.2"
  val logbackClassic = "1.2.7"
  val scalaTest = "3.2.10"
}

object Library {
  val akkaActor = "com.typesafe.akka"      %% "akka-actor"       % Version.akka
  val akkaStream = "com.typesafe.akka"     %% "akka-stream"      % Version.akka
  val slick = "com.typesafe.slick"         %% "slick"            % Version.slick
  val slickHikariCP = "com.typesafe.slick" %% "slick-hikaricp"   % Version.slick
  val postgresql = "org.postgresql"         % "postgresql"       % Version.postgres
  val akkaHttp = "com.typesafe.akka"       %% "akka-http"        % Version.akkaHttp
  val json4sNative = "org.json4s"          %% "json4s-native"    % Version.json4s
  val json4sJackson = "org.json4s"         %% "json4s-jackson"   % Version.json4s
  val akkaHttpJson4s = "de.heikoseeberger" %% "akka-http-json4s" % Version.akkaHttpJson4s
  val logbackClassic = "ch.qos.logback"     % "logback-classic"  % Version.logbackClassic
  val scalaTest = "org.scalatest"          %% "scalatest"        % Version.scalaTest % Test
}

object Dependencies {

  import Library._

  val depends = Seq(
    akkaActor,
    akkaStream,
    slick,
    slickHikariCP,
    postgresql,
    akkaHttp,
    json4sNative,
    json4sJackson,
    akkaHttpJson4s,
    logbackClassic,
    scalaTest
  )

}
