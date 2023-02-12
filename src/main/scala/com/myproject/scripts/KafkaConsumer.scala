package com.myproject.scripts


import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, current_timestamp, to_date, date_format,  expr, from_json,  to_timestamp, trim}
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.sql.streaming.{Trigger}


object KafkaConsumer extends App {

  val confs = new KafkaCustomArgs(args)

  val spark: SparkSession = SparkSession
    .builder()
    .enableHiveSupport()
    .getOrCreate()

  import spark.implicits._

  val streamingDF = spark    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", s"${confs.KAFKA_BOOTSTRAP().mkString(",")}")
    .option("subscribe", s"${confs.KAFKA_TOPICS().mkString(",")}")
    .option("startingOffsets", "earliest")
    .option("kafka.sasl.mechanism", "PLAIN")
    .option("kafka.security.protocol", "SASL_SSL")
    .option("kafka.ssl.truststore.location", s"${confs.TRUSTSTORE_LOCATION()}")
    .option("kafka.ssl.truststore.password", s"${confs.TRUSTSTORE_PASSWORD()}")
    .option("java.security.auth.login.config", s"${confs.KAFKA_JAAS()}")
    .option("kafka.sasl.jaas.config", s"org.apache.kafka.common.security.plain.PlainLoginModule required username='${confs.KAFKA_USERNAME()}'password='${confs.KAFKA_PASSWORD()}';")
    .option("kafka.ssl.endpoint.identification.algorithm", "")
    .load()


  val topicDF = streamingDF.selectExpr("CAST(key AS STRING)","CAST(value AS STRING)")


  val schema = new StructType()
    .add("service", StringType)
    .add("soc", StringType)
    .add("expiration_date", StringType)
    .add("client_id", StringType)
    .add("start_date", StringType)


  val DF = topicDF    .select(from_json(col("value"), schema).as("data"))
    .select("data.*")
    .writeStream
    .option("checkpointLocation", "/warehouse/tablespace/external/hive/stg.db/kafka_log_checkpoints")
    .trigger(Trigger.Once)
    .foreachBatch((DF: DataFrame, idBatch: Long) => {

      val outDF =  DF.select($"*")
        .withColumn("update_ts", to_timestamp(trim(date_format(current_timestamp(),"yyyy-MM-dd HH:mm:ss")) + expr("INTERVAL 3 HOURS")))
        .withColumn("update_date", date_format(to_date($"update_ts", "yyyy-MM-dd HH:mm:ss"), format = "yyyy-MM-dd"))
        .withColumn("update_month", date_format(to_date($"update_ts", "yyyy-MM-dd HH:mm:ss"), format = "yyyy-MM-01"))



      outDF
        .write
        .option("path", "/warehouse/tablespace/external/hive/datamarts.db/kafka_log")
        .partitionBy("update_month","update_date")
        .mode(SaveMode.Append)
        .saveAsTable("datamarts_db.kafka_log")
    })
    .start()

  DF.awaitTermination()
}