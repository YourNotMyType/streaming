package cput.streaming.infrastructure.cassandra.text.impl

import cput.streaming.domain.TextData
import cput.streaming.infrastructure.cassandra.text.TextDataTable
import cput.streaming.util.DataStorageError
import cput.streaming.util.DataStorageError.CassandraError
import io.kaizensolutions.virgil._
import io.kaizensolutions.virgil.dsl._
import zio.{ZIO, ZLayer}

final case class TextDataTableImpl() extends TextDataTable {
  private lazy val TableName = "text_data"

  override def create(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]] =
    InsertBuilder(TableName)
      .value(Id, data.id)
      .value(text, data.text)
      .build
      .executeMutation
      .mapBoth(
        CassandraError,
        result =>
          if (result.result)
            Option(data)
          else
            None,
      )

  override def read(id: String): ZIO[CQLExecutor, DataStorageError, Option[TextData]] =
    SelectBuilder
      .from(TableName)
      .allColumns
      .where(Id === id)
      .build[TextData]
      .execute
      .runHead
      .mapError(CassandraError)

  override def update(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]] =
    InsertBuilder(TableName)
      .value(Id, data.id)
      .value(text, data.text)
      .build
      .executeMutation
      .mapBoth(
        CassandraError,
        result =>
          if (result.result)
            Option(data)
          else
            None,
      )

  override def delete(data: TextData): ZIO[CQLExecutor, DataStorageError, Option[TextData]] =
    DeleteBuilder(TableName)
      .entireRow
      .where(Id === data.id)
      .build
      .executeMutation
      .mapBoth(
        CassandraError,
        result =>
          if (result.result)
            Option(data)
          else
            None,
      )

  override def getAll: ZIO[CQLExecutor, DataStorageError, Seq[TextData]] =
    SelectBuilder
      .from(TableName)
      .allColumns
      .build[TextData]
      .execute
      .runCollect
      .mapBoth(CassandraError, _.toList)
}

object TextDataTableImpl extends (() => TextDataTable) {
  val layer = ZLayer.succeed(TextDataTableImpl())
}
