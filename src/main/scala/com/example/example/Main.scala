package com.example.example

import cats.effect.{ExitCode, IO, IOApp}
import com.example.example.http.BookRoutes
import com.example.example.repository.{BookRepo, Doobie}
import com.example.example.repository.BookRepo.{DoobieImpl, HashImpl}
import org.http4s.server.Router
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import cats.implicits._

object Main extends IOApp {

  private val bookRepo: BookRepo = new DoobieImpl(Doobie.xa)

  val httpRoutes = Router[IO](
    "/" -> BookRoutes.routes(bookRepo)
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO]
      .bindHttp(7777, "0.0.0.0")
      .withHttpApp(httpRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}
