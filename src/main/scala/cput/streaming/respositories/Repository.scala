package cput.streaming.respositories


import cput.streaming.util.DataStorageError
import zio.{IO, ZIO}

trait Repository[A] {
  def create(data: A): IO[DataStorageError, Option[A]]
  def read(id: String): IO[DataStorageError, Option[A]]
  def update(data: A):  IO[DataStorageError, Option[A]]
  def delete(data: A):  IO[DataStorageError, Option[A]]
  def getAll:           IO[DataStorageError, Seq[A]]
}
