package com.myproject.scripts

import com.myproject.commons.EntryPoint

class CustomParams extends EntryPoint {

  val srcTableName      = getAppConf(jobConfigKey, "srcTableName")
  val trgTableName      = getAppConf(jobConfigKey, "trgTableName")
  val loggingTableName  = getAppConf(jobConfigKey, "loggingTableName")
  val fromDate          = getAppConf(jobConfigKey, "fromDate")
  val patternPartitions = getAppConf(jobConfigKey, "patternPartitions")


}