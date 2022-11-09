package cput.streaming.environment

import io.kaizensolutions.virgil._
import io.kaizensolutions.virgil.cql._
import zio.stream.ZStream
import zio.{ZIO, durationInt}

object DBInitialisation {
  private def runMigration(): ZIO[CQLExecutor, Throwable, Unit] = {
    val migrationCql =
      ZStream
        .fromZIO(
          ZIO.attemptBlocking(
            scala.io.Source
              .fromResource("cass-schema.cql")
              .getLines(),
          ),
        )
        .flatMap(ZStream.fromIterator(_))
        .map(_.strip())
        .filterNot { l =>
          l.isEmpty || l.startsWith("--") || l.startsWith("//")
        }
        .runFold("")(_ ++ _)
        .map(_.split(";"))

    for {
      migrations <- migrationCql
      _          <- ZIO.foreachDiscard(migrations)(str => (str.asCql.mutation).timeout(2.minutes).execute.runDrain)
    } yield ()
  }
  def migration: ZIO[CQLExecutor, Throwable, Unit] = runMigration().as((): Unit)
}
