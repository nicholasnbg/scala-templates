package com.example.example.repository
import cats.effect.IO
import doobie._
import doobie.util.transactor.Transactor.Aux

import scala.concurrent.ExecutionContext

object Doobie {

  implicit val cs = IO.contextShift(ExecutionContext.global)

  val xa: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    "org.postgres.Driver", "jbdc:postgres:example", "postgres", "testpass"
  )

}
