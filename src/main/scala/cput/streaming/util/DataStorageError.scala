package cput.streaming.util

import zio.ZIO

sealed trait DataStorageError extends Throwable

object DataStorageError{
  case class  RepositoryError(error: Throwable) extends DataStorageError{
    ZIO.logInfo(s"RepositoryError ${error.getMessage}")
  }
  case class  CassandraError(error: Throwable) extends DataStorageError{
    println(s"The Database Error is ${error}")
    ZIO.logInfo(s"RepositoryError ${error.getMessage}")
  }

}
