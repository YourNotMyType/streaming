import Dependencies._
import com.typesafe.sbt.packager.docker.Cmd

ThisBuild / organization := "cput.streaming"
ThisBuild / version      := "0.0.1"

lazy val dependencies =
  STTP.all ++
    General.all ++
    Config.all ++
    ZIO.all ++
    Logging.all ++
    Testing.all ++
    CassandraDrivers.all

lazy val projectDefinition = Seq(
  name := "ScalaClient",
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  libraryDependencies ++= dependencies,
  scalacOptions ++= Seq("-Ymacro-annotations"),
)
lazy val root              = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(BuildHelper.stdSettings)
  .settings(projectDefinition)
addCommandAlias("fmt", "scalafmt; Test / scalafmt; sFix;")
addCommandAlias("fmtCheck", "scalafmtCheck; Test / scalafmtCheck; sFixCheck")
addCommandAlias("sFix", "scalafix OrganizeImports; Test / scalafix OrganizeImports")
addCommandAlias(
  "sFixCheck",
  "scalafix --check OrganizeImports; Test / scalafix --check OrganizeImports",
)

resolvers += "jitpack" at "https://jitpack.io"
