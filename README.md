## Scala Avro Kafka

Scala project aimed at demonstrating how to publish/consume records to/from Kafka using Avro schemas.

### Running dependencies

From the root of the project run `docker-compose -f docker/docker-compose.yml up -d`

To shutdown all dependencies run `docker-compose -f docker/docker-compose.yml down`

### Kafdrop (Kafka UI)

https://github.com/obsidiandynamics/kafdrop

This will be available [here](http://localhost:9000/) when docker-compose up -d is ran from the docker directory of this
package

This is a useful tool to view the messages on the "person-topic"

### Schema Registry

View all subjects (schemas) [here](http://localhost:8081/subjects/)

View all versions of the person-topic schemas [here](http://localhost:8081/subjects/person-topic-value/versions)

View specific information of a given version of the schema by hitting
an [endpoint like this](http://localhost:8081/subjects/person-topic-value/versions/1)
