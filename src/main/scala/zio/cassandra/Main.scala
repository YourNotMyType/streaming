package zio.cassandra

import zio._
import zio.{Console, Scope, ZIO, ZIOAppDefault, ZLayer}
import com.datastax.oss.driver.api.core.cql.{Row, SimpleStatement}
import zio.cassandra.*
import zio.cassandra.{ZCqlSessionSpec, ZCqlSessionStreamSpec}


case class DatabaseConfig()

object DatabaseConfig {
  val live = ZLayer.succeed(DatabaseConfig())
}

case class Database(databaseConfig: DatabaseConfig)

object Database {
  val live: ZLayer[DatabaseConfig, Nothing, Database] = //Scope, CassandraException, ZCqlSession
    ZLayer
      .scoped(
        session.auto
          .open(
          "127.0.0.1",
          9042,
          "painters_keyspace",
          )
          .tap(session => ZCqlSessionSpec.initialize(session) <&> ZCqlSessionStreamSpec.initialize(session))
      )
    ZLayer.fromFunction(Database.apply _)
}

case class Analytics()

object Analytics {
  val live: ULayer[Analytics] = ZLayer.succeed(Analytics())
}

case class Users(database: Database, analytics: Analytics)

object Users {
  val live = ZLayer.fromFunction(Users.apply _)
}

case class App(users: Users, analytics: Analytics) {
  def execute: UIO[Unit] =
    ZIO.debug(s"This app is made from ${users} and ${analytics}")
}

object App {
  val live = ZLayer.fromFunction(App.apply _)
}

object MainApp extends ZIOAppDefault {

  def run =
    ZIO
      .serviceWithZIO[App](_.execute)
      // Cannot use `provide` due to this dotty bug: https://github.com/lampepfl/dotty/issues/12498
      .provideLayer(
        (((DatabaseConfig.live >>> Database.live) ++ Analytics.live >>> Users.live) ++ Analytics.live) >>> App.live
      )
}