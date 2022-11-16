package cput.streaming.main

import cput.streaming.domain.TextData
import cput.streaming.infrastructure.cassandra.text.impl.TextDataTableImpl
import cput.streaming.testdata.TestConnection.testConnectionDB
import zio.{Scope, System}
import zio.test.TestAspect.samples
import zio.test._
import zio.test.magnolia.DeriveGen

import java.lang

object TestClientSpec extends zio.test.ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Test Text Data ")(
      test("Test Text Data ") {
        val startTime = lang.System.currentTimeMillis()
        (1 to 100000). foreach{ x =>
          TextData(x.toString,"Object Created")
        }
        val endTime  = lang.System.currentTimeMillis()

        println(s"It tool this Long to Run ${endTime- startTime} ms")
        val sum = 1 + 5
        assertTrue( sum  == 6)

        }
    )

}
