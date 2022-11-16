package cput.streaming.testdata

import com.datastax.oss.driver.api.core.CqlSession
import io.kaizensolutions.virgil._
import io.kaizensolutions.virgil.cql._
import zio.stream.ZStream
import zio.{&, Task, ULayer, URLayer, ZIO, ZLayer}

import java.net.InetSocketAddress

object TestConnection {
  def runMigration(cql: CQLExecutor, fileName: String): Task[Unit]   = {
    val migrationCql =
      ZStream
        .fromZIO(ZIO.attemptBlocking(scala.io.Source.fromResource(fileName).getLines()))
        .flatMap(ZStream.fromIterator(_))
        .map(_.strip())
        .filterNot { l =>
          l.isEmpty ||
          l.startsWith("--") ||
          l.startsWith("//")
        }
        .runFold("")(_ ++ _)
        .map(_.split(";"))

    for {
      migrations <- migrationCql
      _          <- ZIO.foreachDiscard(migrations)(str => cql.execute(str.asCql.mutation).runDrain)
    } yield ()
  }
  val testConnectionDB: ULayer[TestCassandraContainer & CQLExecutor] = {
    val keyspaceAndMigrations =
      for {
        c       <- ZIO.service[TestCassandraContainer]
        details <- (c.getHost).zip(c.getPort)
        (host, port) = details
        session <- CQLExecutor(
          CqlSession
            .builder()
            .withLocalDatacenter("dc1")
            .addContactPoint(InetSocketAddress.createUnresolved(host, port)),
        )
        createKeyspace =
          cql"""CREATE KEYSPACE IF NOT EXISTS stream
          WITH REPLICATION = {
            'class': 'SimpleStrategy',
            'replication_factor': 1
          }""".mutation
        useKeyspace    = cql"USE stream".mutation
        _ <- session.execute(createKeyspace).runDrain
        _ <- session.execute(useKeyspace).runDrain
        _ <- runMigration(session, "text-schema.cql")
      } yield session

    val containerLayer: ULayer[TestCassandraContainer]             =
      ZLayer.scoped(TestCassandraContainer(CassandraType.Plain))
    val sessionLayer: URLayer[TestCassandraContainer, CQLExecutor] =
      ZLayer.scoped(keyspaceAndMigrations).orDie
    containerLayer >+> sessionLayer
  }
}
