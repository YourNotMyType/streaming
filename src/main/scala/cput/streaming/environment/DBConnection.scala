package cput.streaming.environment

import com.datastax.oss.driver.api.core.config.{DefaultDriverOption, DriverConfigLoader}
import com.datastax.oss.driver.api.core.{CqlSession, CqlSessionBuilder}
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableMap
import com.typesafe.config.{Config, ConfigFactory}
import io.kaizensolutions.virgil.CQLExecutor
import org.redisson.config.{TransportMode, Config => RedisConfig}
import zio.{ULayer, ZLayer}

import java.net.InetSocketAddress
import java.time.Duration
import scala.jdk.CollectionConverters._

object DBConnection {
  val config: Config          = ConfigFactory.load()
  def PORT                    = 9042
  def connectionTimeoutMillis = 70000000
  def readTimeoutMillis       = 150000000

  // HOSTS
  val productionHost: Seq[String]  = config.getStringList("cassandra.productionHost").asScala.toList
  // Data Centers
  val dataCenter1: String          = config.getString("cassandra.dataCenter1")
  val keySpace: String               = config.getString("cassandra.keySpace")




  // The test Container parameters
  val loader                                  =
    DriverConfigLoader
      .programmaticBuilder()
      .withDuration(
        DefaultDriverOption.REQUEST_TIMEOUT,
        Duration.ofSeconds(connectionTimeoutMillis),
      )
      .startProfile("slow")
      .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(readTimeoutMillis))
      .endProfile()
      .build()

  lazy val prodConnector: ULayer[CQLExecutor] = {
    val cqlSessionBuilderLayer: ULayer[CqlSessionBuilder] = ZLayer.succeed(
      CqlSession
        .builder()
        .withConfigLoader(loader)
        .withKeyspace(keySpace)
        .withLocalDatacenter(dataCenter1)
        .addContactPoint(InetSocketAddress.createUnresolved(productionHost(0), PORT))
        .withApplicationName("stream"),
    )
    val executor: ZLayer[Any, Throwable, CQLExecutor]     =
      cqlSessionBuilderLayer >>> CQLExecutor.live
    executor.orDie
  }



}
