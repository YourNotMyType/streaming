package cput.streaming.infrastructure.cassandra

import cput.streaming.util.DataStorageError
import io.kaizensolutions.virgil.CQLExecutor
import zio.ZIO

trait Infrastructure[A] {
  def create(data: A): ZIO[CQLExecutor, DataStorageError, Option[A]]

  def read(id: String): ZIO[CQLExecutor, DataStorageError, Option[A]]

  def update(data: A): ZIO[CQLExecutor, DataStorageError, Option[A]]

  def delete(data: A): ZIO[CQLExecutor, DataStorageError, Option[A]]

  def getAll: ZIO[CQLExecutor, DataStorageError, Seq[A]]
}
