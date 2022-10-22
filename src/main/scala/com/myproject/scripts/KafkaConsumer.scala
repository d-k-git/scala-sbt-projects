package com.myproject.scripts

import com.myproject.classes.Params
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}


object KafkaConsumer extends Params {


  override val spark: SparkSession = SparkSession.builder()
    .appName("KafkaConsumer")
    .master("yarn")
    .getOrCreate()

  import spark.implicits._

  // https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html
  // https://stackoverflow.com/questions/61481628/spark-structured-streaming-with-kafka-sasl-plain-authentication


  val df = spark.read.format("kafka")
    .option("kafka.bootstrap.servers", "**.**.***.**:0000")
    .option("kafka.sasl.mechanism", "PLAIN")
    .option("kafka.security.protocol", "SASL_PLAINTEXT")
    .option("kafka.ssl.truststore.location","/home/user/server.truststore.jks")
    .option("kafka.ssl.truststore.password","*****")
    .option("kafka.sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"developer\\\" password=\\\"888888\\\"")
    .option("subscribe", "yd.log.messages.1")
    .load()



  val query = df.writeStream
    .outputMode("append")
    .format("console")
    .start().awaitTermination();

  val outDF = df
    .select(
      col("client_id"),
      col("service"),
      col("file"),
      col("start_date"),
      col("end_date")
    )
    .withColumn("start_date", date_format(to_date($"start_date", "dd.MM.yyyy"), format = "yyyy-MM-dd"))
    .withColumn("end_date", date_format(to_date($"end_date", "dd.MM.yyyy"), format = "yyyy-MM-dd"))

  outDF
    .write
    .mode(SaveMode.Append)
    .saveAsTable(TABLE_NAME_1)


  spark.stop()


}