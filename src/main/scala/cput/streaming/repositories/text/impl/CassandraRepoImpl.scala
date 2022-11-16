package cput.streaming.repositories.text.impl

import com.datastax.oss.driver.api.core.cql.SimpleStatement
import cput.streaming.domain.TextData
import cput.streaming.environment.DBConnection.prodConnector
import cput.streaming.infrastructure.cassandra.text.TextDataTable
import cput.streaming.repositories.text.CassandraRepo
import cput.streaming.util.DataStorageError
import zio.{IO, ZIO, ZLayer}

case class CassandraRepoImpl (textDataTable: TextDataTable) extends CassandraRepo {
  override def create(data: TextData): IO[DataStorageError, Option[TextData]] =
    textDataTable.create(data).provide(prodConnector)

  override def read(id: String): IO[DataStorageError, Option[TextData]] = ???

  override def update(data: TextData): IO[DataStorageError, Option[TextData]] = ???

  override def delete(data: TextData): IO[DataStorageError, Option[TextData]] = ???

  override def getAll: IO[DataStorageError, Seq[TextData]] = ???
}
object CassandraRepoImpl{
  lazy val layer = ZLayer{
    for{
      textDataTable <- ZIO.service[TextDataTable]
    } yield CassandraRepoImpl(textDataTable)
  }
}
