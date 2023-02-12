package com.myproject.scripts

import org.rogach.scallop._

class KafkaConsumerArgs(arguments: Seq[String]) extends ScallopConf(arguments) {
    val KAFKA_USERNAME: ScallopOption[String] = opt[String](required = true, name = "kafka-username")
    val KAFKA_PASSWORD: ScallopOption[String] = opt[String](required = true, name = "kafka-password")
    val TRUSTSTORE_PASSWORD: ScallopOption[String] = opt[String](required = true, name = "truststore-password")
    val TRUSTSTORE_LOCATION: ScallopOption[String] = opt[String](required = true, name = "truststore-location")
    val KAFKA_BOOTSTRAP: ScallopOption[List[String]] = opt[List[String]](required = true, name = "kafka-bootstrap")
    val KAFKA_TOPICS: ScallopOption[List[String]] = opt[List[String]](required = true, name = "kafka-topics")
    val KAFKA_JAAS: ScallopOption[String] = opt[String](required = true, name = "jaas-conf-location")
    val OUTPUT_PATH: ScallopOption[String] = opt[String](required = true, name = "output_path")
    val OUTPUT_TABLE: ScallopOption[String] = opt[String](required = true, name = "output_table")

    verify()

}
