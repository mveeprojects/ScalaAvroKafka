package conf

import pureconfig.ConfigSource
import pureconfig.generic.auto._

trait Config {
  case class KafkaConfig(brokerurl: String, schemaregurl: String, topic: String, groupid: String)

  case class AppConfig(kafka: KafkaConfig)

  val appConfig: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
}
