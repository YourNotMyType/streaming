import sbt._

object Dependencies {
  private val test = Test

  object General {
    private val commonsVersion = "3.12.0"
    val slf4jVersion = "2.0.0"
    private val configVersion = "1.4.2"
    private val simpleFv = "5.1.6"
    private val scalaCacheV = "1.0.0-M6"
    private val commonsIOV = "2.11.0"
    private val parserCombinatorsVersion = "2.1.1"
    private val redissonVersion = "3.17.6"
    private val caffeineVersion = "3.1.1"
    private val sCaffeineVersion = "5.2.1"

    val parserCombinators =
      "org.scala-lang.modules" %% "scala-parser-combinators" % parserCombinatorsVersion
    val apacheCommons = "org.apache.commons" % "commons-lang3" % commonsVersion
    val CommonsIO = "commons-io" % "commons-io" % commonsIOV
    val scalaCache = "com.github.cb372" %% "scalacache-core" % scalaCacheV
    val config = "com.typesafe" % "config" % configVersion
    val simpleSlf4j = "org.slf4j" % "slf4j-routes" % slf4jVersion
    val slf4j = "org.slf4j" % "slf4j-simple" % slf4jVersion
    val simpleFramework = "org.simpleframework" % "simple" % simpleFv
    val redisson = "org.redisson" % "redisson" % redissonVersion
    val caffeine = "com.github.ben-manes.caffeine" % "caffeine" % caffeineVersion
    val sCaffeine = "com.github.blemale" %% "scaffeine" % sCaffeineVersion
    val nscala = "com.github.nscala-time" %% "nscala-time" % "2.32.0"
    val all: Seq[ModuleID] =
      Seq(
        apacheCommons,
        config,
        CommonsIO,
        nscala,
        scalaCache,
        simpleFramework,
        redisson,
        sCaffeine,
      )
  }

  object ZIO {
    val zioVersion = "2.0.2"
    val zioJsonVersion = "0.3.0-RC10"
    val interopVersion = "13.0.0.1"
    val loggingVersion = "2.1.0"
    val zioMetricsConnectorsVersion = "2.0.0-RC6"
    val zioCacheVersion = "0.2.0"
    val zio = "dev.zio" %% "zio" % zioVersion
    val streams = "dev.zio" %% "zio-streams" % zioVersion
    val macros = "dev.zio" %% "zio-macros" % zioVersion
    val catsInterop = "dev.zio" %% "zio-interop-cats" % interopVersion
    val logging = "dev.zio" %% "zio-logging" % loggingVersion
    val loggingSlf4j = "dev.zio" %% "zio-logging-slf4j" % loggingVersion
    val zioJson = "dev.zio" %% "zio-json" % zioJsonVersion
    val zioMetrics = "dev.zio" %% "zio-metrics-connectors" % zioMetricsConnectorsVersion
    val zioCache = "dev.zio" %% "zio-cache" % zioCacheVersion

    val all: Seq[ModuleID] =
      Seq(zio, streams, zioCache, macros, catsInterop, logging, loggingSlf4j, zioJson, zioMetrics)
  }


  object STTP {
    val sttpVersion = "3.8.2"

    val zioClient = "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % sttpVersion
    val clientCirce = "com.softwaremill.sttp.client3" %% "circe" % sttpVersion
    val core = "com.softwaremill.sttp.client3" %% "core" % sttpVersion
    val all: Seq[ModuleID] = Seq(zioClient, clientCirce, core)
  }


  object Config {
    private val version = "0.17.1"
    private val refinedV = "0.10.1"
    val pureconfig = "com.github.pureconfig" %% "pureconfig" % version
    val pureconfigRefined = "eu.timepit" %% "refined-pureconfig" % refinedV
    val all: Seq[ModuleID] = Seq(pureconfig, pureconfigRefined)
  }

  object Logging {
    private val logbackV = "1.4.0"
    private val typeSafeV = "3.9.5"
    private val zioLoggingV = "2.1.0"
    val Core = "ch.qos.logback" % "logback-core" % logbackV
    val Logback = "ch.qos.logback" % "logback-classic" % logbackV
    val typeSafe = "com.typesafe.scala-logging" %% "scala-logging" % typeSafeV
    val zioLogging = "dev.zio" %% "zio-logging" % zioLoggingV
    val zioLoggingSlf4j = "dev.zio" %% "zio-logging-slf4j" % zioLoggingV
    val all: Seq[ModuleID] = Seq(Core, Logback, typeSafe, zioLogging, zioLoggingSlf4j)
  }

  object Testing {
    private val scalaTestVersion = "3.2.12"
    private val testCassandraContainerVersion = "1.17.3"
    private val testContainerVersion = "0.40.10"
    private val scalaTestPlusVersion = "5.0.0"
    private val zioMockVersion = "1.0.0-RC8"
    val zioTest = "dev.zio" %% "zio-test" % ZIO.zioVersion % test
    val zioTestSbt = "dev.zio" %% "zio-test-sbt" % ZIO.zioVersion % test
    val zioTestJunit = "dev.zio" %% "zio-test-junit" % ZIO.zioVersion % test
    val zioTestMagnolia = "dev.zio" %% "zio-test-magnolia" % ZIO.zioVersion % test
    val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % test
    val zioClient =
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % STTP.sttpVersion % test
    val clientCirce = "com.softwaremill.sttp.client3" %% "circe" % STTP.sttpVersion % test
    val core = "com.softwaremill.sttp.client3" %% "core" % STTP.sttpVersion % test
    val zioMock = "dev.zio" %% "zio-mock" % zioMockVersion % test
    val testContainers = "com.dimafeng" %% "testcontainers-scala" % testContainerVersion % test
    val testContainer = "org.testcontainers" % "cassandra" % testCassandraContainerVersion % test
    val simpleSlf4j = "org.slf4j" % "slf4j-api" % General.slf4jVersion % test
    val slf4j = "org.slf4j" % "slf4j-simple" % General.slf4jVersion % test

    val all: Seq[ModuleID] =
      Seq(
        zioTest,
        zioTestSbt,
        zioTestMagnolia,
        zioClient,
        clientCirce,
        core,
        testContainer,
        testContainers,
        zioMock,
        zioTestJunit,
        simpleSlf4j,
        slf4j,
      )
  }

  object CassandraDrivers {
    private val versionQueryBuilder = "4.14.1"
    private val virgilVersion = "0.10.2-zio2"
    private val pulsarIoCassandraVersion = "2.10.2"
    private val apacheKafkaVersion = "3.3.1"
    private val dataStaxJavaCassandraVersion = "4.15.0"

    val virgil = "com.github.kaizen-solutions.virgil" %% "virgil" % virgilVersion
    val dataStaxQueryBuilder = "com.datastax.oss" % "java-driver-query-builder" % versionQueryBuilder
    val dataStaxJavaCassandra = "com.datastax.oss" % "java-driver-core" % dataStaxJavaCassandraVersion
    val pulsarIoCassandra = "org.apache.pulsar" % "pulsar-io-cassandra" % pulsarIoCassandraVersion
    val apacheKafka = "org.apache.kafka" % "kafka-clients" % apacheKafkaVersion
    val all: Seq[ModuleID] = Seq(virgil, dataStaxQueryBuilder, dataStaxJavaCassandra,pulsarIoCassandra, apacheKafka)
  }
}