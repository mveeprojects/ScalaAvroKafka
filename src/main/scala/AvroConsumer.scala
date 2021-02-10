import com.sksamuel.avro4s.FromRecord
import conf.Config
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import utils.Logging

import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters._

object AvroConsumer extends App with Config with Logging {

  val props: Properties = setupKafkaProps

  consumeFromKafka

  private def consumeFromKafka()(implicit personFromRecord: FromRecord[Person]) {
    val consumer: KafkaConsumer[String, GenericRecord] = new KafkaConsumer[String, GenericRecord](props)
    consumer.subscribe(List(appConfig.kafka.topic).asJava)
    val records = consumer.poll(Duration.ofSeconds(1))
    logger.info(s"Received ${records.count()} records")

    records.iterator.asScala.foreach { record =>
      val genericPerson: GenericRecord = record.value
      val person: Person = Person(
        genericPerson.get("name").toString,
        genericPerson.get("age").asInstanceOf[Int]
      )
      logger.info("Person received:")
      logger.info(person.toString)
    }
  }

  private def setupKafkaProps: Properties = {
    val props: Properties = new Properties()
    props.put("bootstrap.servers", appConfig.kafka.brokerurl)
    props.put("group.id", appConfig.kafka.groupid)
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer")
    props.put("schema.registry.url", appConfig.kafka.schemaregurl)
    props
  }
}
