package com.myproject.commons

import org.apache.spark.sql.SparkSession

class EntryPoint extends App  {

  val spark: SparkSession = SparkSession.builder
    .master("yarn")
    .config("hive.exec.dynamic.partition", "true")
    .config("spark.sql.parquet.writeLegacyFormat","true")
    .config("","")
    .enableHiveSupport()
    .getOrCreate()


  val jobConfigKey = "spark." + (spark.sparkContext.appName) + "."


  def getAppConf(key: String, field: String) = {
    spark.conf.get(key + field)
  }
}
