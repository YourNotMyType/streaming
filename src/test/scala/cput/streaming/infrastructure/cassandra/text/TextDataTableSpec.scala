package cput.streaming.infrastructure.cassandra.text

import cput.streaming.domain.TextData
import cput.streaming.infrastructure.cassandra.text.impl.TextDataTableImpl
import cput.streaming.testdata.TestConnection.testConnectionDB
import zio.Scope
import zio.test.TestAspect.samples
import zio.test._
import zio.test.magnolia.DeriveGen

object TextDataTableSpec extends zio.test.ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Test Text Data ")(
      test("Test Text Data ") {
        check(DeriveGen[TextData]) { data =>
          for {
            create <- TextDataTable.create(data)
            read <- TextDataTable.read(data.id)
            update <- TextDataTable.update(data)
            readUpdate <- TextDataTable.read(data.id)
            readAll <- TextDataTable.getAll
            delete <- TextDataTable.delete(data)
          } yield assert(create)(Assertion.isSome) &&
            assertTrue(read.isDefined) &&
            assertTrue(update.isDefined) &&
            assertTrue(readUpdate.isDefined) &&
            assertTrue(readAll.nonEmpty) &&
            assert(delete)(Assertion.isSome)
        }
      } @@ samples(1),
    ).provide(testConnectionDB ++ TextDataTableImpl.layer)
}
