package cput.streaming.infrastructure.cassandra.text

import cput.streaming.domain.TextData
import cput.streaming.infrastructure.cassandra.Infrastructure
import cput.streaming.util.DataStorageError
import io.kaizensolutions.virgil.CQLExecutor
import zio.ZIO
import zio.macros.accessible

@accessible[TextData]
trait TextDataTable extends Infrastructure[TextData]{
  def Id = "id"
  def text = "date"
  def create(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]]
  def read(id: String): ZIO[CQLExecutor, DataStorageError, Option[TextData]]
  def update(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]]
  def delete(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]]
  def getAll: ZIO[CQLExecutor, DataStorageError, Seq[TextData]]
}
