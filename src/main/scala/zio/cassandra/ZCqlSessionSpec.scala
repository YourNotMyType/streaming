package zio.cassandra

import com.datastax.oss.driver.api.core.cql.{Row, SimpleStatement}
import zio.cassandra.CassandraException.EmptyResultSetException
import zio.cassandra.*
import zio.ZIO


object ZCqlSessionSpec {

  private val tableName = "painters_by_country"
  private val dropTable = SimpleStatement.builder(s"DROP TABLE IF EXISTS $tableName;").build

  private val createTable =
    SimpleStatement
      .builder(
        s"""
           |CREATE TABLE IF NOT EXISTS $tableName (
           |  country text,
           |  name text,
           |  PRIMARY KEY (country, name)
           |);
           |""".stripMargin
      )
      .build

  def initialize(session: ZCqlSession) = session.execute(dropTable) *> session.execute(createTable)

  private val insertStatement = ZStatement(s"INSERT INTO $tableName (country, name) VALUES (?,?);")

  private val ARGENTINA = "Argentina"
  private val BRAZIL    = "Brazil"
  private val FRANCE    = "France"
  private val MEXICO    = "Mexico"
  private val SPAIN     = "Spain"

  private case class Painter(country: String, name: String)

  private def insert(painter: Painter) = insertStatement.bind(painter.country, painter.name)
  
  private val frida: Painter = Painter(MEXICO, "Frida Kahlo")
  ZCqlSession.untyped.execute(insert(frida))

  private val xul: Painter = Painter(ARGENTINA, "Xul Solar")
  ZCqlSession.untyped.execute(insert(xul))

  private val tarsila: Painter = Painter(BRAZIL, "Tarsila do Amaral")
  ZCqlSession.untyped.execute(insert(tarsila))



}
