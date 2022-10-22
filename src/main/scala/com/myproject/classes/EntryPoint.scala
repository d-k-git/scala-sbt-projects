package com.myproject.classes


import org.apache.spark.sql.SparkSession

class EntryPoint extends App with LightBendConf {

  val spark: SparkSession = SparkSession.builder
    .master("yarn")
    .config("hive.exec.dynamic.partition", "true")
    .config("","")
    .enableHiveSupport()
    .getOrCreate()

  val jobConfig = appProps(spark.sparkContext.appName)


  def getAppConf(key: String, field: String) = {
    spark.conf.get(key + field, jobConfig.getString(field))
  }
}
