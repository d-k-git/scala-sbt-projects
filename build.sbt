name := "DataSets"

version := "0.1.0"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.1"


resolvers += "Nexus local" at "https://nexus-repo.com/repository/sbt_releases_/"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-avro" % sparkVersion,
  "org.rogach" %% "scallop" % "3.5.1" % "provided",
  "com.typesafe" % "config" % "1.4.1")



assemblyMergeStrategy in assembly := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
