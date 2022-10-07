package zio.cassandra

import zio.{Console, Scope, ZIO, ZIOAppDefault, ZLayer}
import com.datastax.oss.driver.api.core.cql.{Row, SimpleStatement}
import zio.cassandra.*
import zio.cassandra.CassandraException.SessionOpenException

object Main extends ZIOAppDefault {
  def run = Console.printLine("Hello, world!")


  val sessionCassandra: ZIO[Scope, SessionOpenException, ZCqlSession] =
    session.auto.open(
      "127.0.0.1",
      9042,
      "painters_keyspace",
    )

  val layer: ZLayer[Scope, SessionOpenException, ZCqlSession] =
    ZLayer.scoped(sessionCassandra)


}
