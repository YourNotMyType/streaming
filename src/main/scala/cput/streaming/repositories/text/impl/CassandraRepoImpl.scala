package cput.streaming.repositories.text.impl

import com.datastax.oss.driver.api.core.cql.SimpleStatement
import cput.streaming.domain.TextData
import cput.streaming.repositories.text.CassandraRepo
import cput.streaming.util.DataStorageError

case class CassandraRepoImpl () extends CassandraRepo{

  private val tableName = "text_data"

  override def create(data: TextData): IO[DataStorageError, Option[TextData]] =
    SimpleStatement.builder(s"INSERT INTO $tableName (id, text) VALUES (${data.id},${data.text});").build

  override def read(id: String): IO[DataStorageError, Option[TextData]] =
    SimpleStatement.builder(s"SELECT * FROM $tableName WHERE id=$id;").build

  override def update(data: TextData): IO[DataStorageError, Option[TextData]] =
    SimpleStatement.builder(s"UPDATE $tableName SET text=${data.text} WHERE id=${data.id} IF EXISTS;").build

  override def delete(data: TextData): IO[DataStorageError, Option[TextData]] =
    SimpleStatement.builder(s"DELETE FROM $tableName WHERE id=${data.id} IF EXISTS;").build

  override def getAll: IO[DataStorageError, Seq[TextData]] =
    SimpleStatement.builder(s"SELECT * FROM $tableName;").build

}
object CassandraRepoImpl{
  val layer = ZLayer.succeed(CassandraRepoImpl())
}
