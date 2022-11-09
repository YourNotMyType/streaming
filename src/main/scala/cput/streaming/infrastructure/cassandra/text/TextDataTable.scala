package cput.streaming.infrastructure.cassandra.text

import cput.streaming.domain.TextData
import cput.streaming.infrastructure.cassandra.Infrastructure
import zio.macros.accessible

@accessible
trait TextDataTable extends Infrastructure[TextData]{
  def Id = "id"
  def text = "date"
}
