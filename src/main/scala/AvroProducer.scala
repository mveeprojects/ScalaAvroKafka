import com.sksamuel.avro4s.{AvroSchema, ToRecord}
import conf.Config
import org.apache.avro.Schema
import org.apache.avro.generic._
import org.apache.kafka.clients.producer._
import utils.Logging

import java.util.Properties
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object AvroProducer extends App with Config with Logging {

  val schema: Schema                                 = AvroSchema[Person]
  val props: Properties                              = setupKafkaProps
  val producer: KafkaProducer[String, GenericRecord] = new KafkaProducer[String, GenericRecord](props)

  List(
    Person("Mark", 21),
    Person("Bob", 33),
    Person("Sally", 42)
  ).map(writeToKafka)

  shutdownProducer()

  private def writeToKafka(person: Person)(implicit personToRecord: ToRecord[Person]): Unit = {

    val genericPerson: GenericRecord = new GenericData.Record(schema)
    genericPerson.put("name", person.name)
    genericPerson.put("age", person.age)

    val record: ProducerRecord[String, GenericRecord] = new ProducerRecord(appConfig.kafka.topic, genericPerson)

    val sendFuture = Future(producer.send(record))

    Await.ready(sendFuture, 2.seconds).value.get match {
      case Success(_) =>
        logger.info("Published record to Kafka successfully")
      case Failure(exception) =>
        logger.error(s"Publishing failed with exception: ${exception.getMessage}")
    }
  }

  private def setupKafkaProps: Properties = {
    val props: Properties = new Properties()
    props.put("bootstrap.servers", appConfig.kafka.brokerurl)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("schema.registry.url", appConfig.kafka.schemaregurl)
    props
  }

  private def shutdownProducer(): Unit = {
    logger.info("Shutting down producer")
    producer.close()
  }
}
