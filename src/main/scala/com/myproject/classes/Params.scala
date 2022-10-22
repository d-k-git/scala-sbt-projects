package com.myproject.classes


class Params extends  EntryPoint {

  def sparkConfGet(conf: String):String = {
    try {
      spark.conf.get(JOB_CONFIG_KEY + conf)
    }
    catch {
      case _ : Throwable => jobConfig.getString(conf)
    }
  }

  val APP_NAME = spark.sparkContext.appName
  val JOB_CONFIG_KEY = s"spark.${APP_NAME}."

  val TABLE_NAME_1 = sparkConfGet("tableLogDaily")
  val TABLE_NAME_2 = sparkConfGet("")

}
