name := "spark-postgresql"

version := "0.1"

scalaVersion := "2.12.11"

val sparkVersion = "2.4.5"

val postgresqlVersion = "42.2.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.postgresql" % "postgresql" % postgresqlVersion
)
