package zio.cassandra

import com.datastax.oss.driver.api.core.cql.{ Row, SimpleStatement }
import zio.cassandra.*
import zio.ZIO

object ZCqlSessionStreamSpec {

  private val tableName = "painters_by_region"

  private val dropTable = SimpleStatement.builder(s"DROP TABLE IF EXISTS $tableName;").build

  private val createTable =
    SimpleStatement
      .builder(
        s"""
           |CREATE TABLE IF NOT EXISTS $tableName (
           |  region text,
           |  name text,
           |  PRIMARY KEY (region, name)
           |);
           |""".stripMargin
      )
      .build

  def initialize(session: ZCqlSession) =
    session.execute(dropTable) *> session.execute(createTable) *> populate(session)

  private val insertStatement =
    SimpleStatement.builder(s"INSERT INTO $tableName (region, name) VALUES (?,?);").build

  private val EUROPE        = "Europe"
  private val LATIN_AMERICA = "Latin America"

  private val frida   = Painter(LATIN_AMERICA, "Frida Kahlo")
  private val xul     = Painter(LATIN_AMERICA, "Xul Solar")
  private val tarsila = Painter(LATIN_AMERICA, "Tarsila do Amaral")
  private val benito  = Painter(LATIN_AMERICA, "Benito Quinquela Martín")
  private val zilia   = Painter(LATIN_AMERICA, "Zilia Sánchez Domínguez")
  private val berni   = Painter(LATIN_AMERICA, "Antonio Berni")
  private val berthe  = Painter(EUROPE, "Berthe Morisot")
  private val monet   = Painter(EUROPE, "Claude Monet")
  private val varo    = Painter(EUROPE, "Remedios Varo")
  private val dali    = Painter(EUROPE, "Salvador Dalí")
  private val leBrun  = Painter(EUROPE, "Élisabeth Le Brun")
  private val miro    = Painter(EUROPE, "Joan Miró")

  private val painters = List(frida, xul, tarsila, benito, zilia, berni, berthe, monet, varo, dali, leBrun, miro)

  private def populate(session: ZCqlSession) =
    session
      .prepare(insertStatement)
      .flatMap(ps => session.executePar(painters.map(painter => ps.bind(painter.region, painter.name))*))

  private case class Painter(region: String, name: String)

}
