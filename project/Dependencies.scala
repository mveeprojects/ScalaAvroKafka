import sbt._

object Dependencies {

  val kafkaDependencies = Seq(
    "org.apache.kafka"    %% "kafka"                 % Versions.kafka,
    "com.sksamuel.avro4s" %% "avro4s-core"           % Versions.avro4s,
    "io.confluent"         % "kafka-avro-serializer" % Versions.confluent
  )

  val configDependencies = Seq(
    "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig
  )

  val loggingDependencies = Seq(
    "ch.qos.logback"              % "logback-classic" % Versions.logback,
    "com.typesafe.scala-logging" %% "scala-logging"   % Versions.scalaLoggingVersion
  )

  object Versions {
    val kafka               = "2.7.0"
    val avro4s              = "4.0.4"
    val confluent           = "5.3.0"
    val pureConfig          = "0.14.0"
    val logback             = "1.2.3"
    val scalaLoggingVersion = "3.9.2"
  }
}
