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
    suite("Test Feed Site on ")(
      test("Test Feed Site is On ") {
        check(DeriveGen[TextData]) { data =>
          for {
            create <- TextDataTable.create(data)
            entity <- TextDataTable.read(data.id)
            delete <- TextDataTable.update(data)
            delete <- TextDataTable.read(data)
            delete <- TextDataTable.getAll
            delete <- TextDataTable.delete(data)
          } yield assert(create)(Assertion.equalTo(data)) &&
            assertTrue(entity.nonEmpty) &&
            assert(delete)(Assertion.equalTo(data))
        }
      } @@ samples(1),
    ).provide(testConnectionDB ++ TextDataTableImpl.layer)
}
