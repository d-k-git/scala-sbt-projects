name := "DataSets"

version := "0.55.0"

scalaVersion := "2.11.12"

val sparkVersion = "2.3.2"
val log4jVersion = "2.14.1"

resolvers += "Nexus local" at "https://nexus-repo.com/repository/sbt_releases_/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "com.typesafe" % "config" % "1.4.1",
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "io.circe" %% "circe-core" % "0.12.0-M3",
  "io.circe" %% "circe-parser" % "0.12.0-M3",
  "io.circe" %% "circe-generic" % "0.12.0-M3",

)



assemblyMergeStrategy in assembly := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
