package cput.streaming.respositories.text.impl

import cput.streaming.domain.TextData
import cput.streaming.respositories.text.CassandraRepo
import cput.streaming.util.DataStorageError
import zio._

case class CassandraRepoImpl () extends CassandraRepo{
  override def create(data: TextData): IO[DataStorageError, Option[TextData]] = ???

  override def read(id: String): IO[DataStorageError, Option[TextData]] = ???

  override def update(data: TextData): IO[DataStorageError, Option[TextData]] = ???

  override def delete(data: TextData): IO[DataStorageError, Option[TextData]] = ???

  override def getAll: IO[DataStorageError, Seq[TextData]] = ???
}
object CassandraRepoImpl{
  val layer = ZLayer.succeed(CassandraRepoImpl())
}
