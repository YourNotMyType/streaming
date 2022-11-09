package cput.streaming.testdata

import zio.mock.MockSpecDefault
import zio.test.Gen
import zio.test.magnolia.DeriveGen
import zio.test.magnolia.DeriveGen._

import java.time.LocalDateTime

trait MockTest extends MockSpecDefault {
  implicit val genLocalDateTime: DeriveGen[LocalDateTime] = instance(
    Gen.localDateTime.map(_ => LocalDateTime.now()),
  )
  implicit val genArbitraryString: DeriveGen[String] = instance(Gen.alphaNumericStringBounded(3, 8))
  implicit val genArbitraryInt: DeriveGen[Int]       = instance(Gen.int(1, 10))
  implicit val genBigDecimal: DeriveGen[BigDecimal]  = instance(
    Gen.bigDecimal(
      BigDecimal(Double.MinValue) * BigDecimal(Double.MaxValue),
      BigDecimal(Double.MaxValue) * BigDecimal(Double.MaxValue),
    ),
  )
}
