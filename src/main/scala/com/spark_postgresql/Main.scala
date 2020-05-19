package com.spark_postgresql

import java.util.Properties

import org.apache.spark.sql.functions.{concat, lit}
import org.apache.spark.sql.{SaveMode, SparkSession}

object Main {

  def main(args: Array[String]): Unit = {
    // Create spark session
    val spark = SparkSession.builder()
      .appName("CSV to DB")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    // Reading the CSV file
    val pathToCsv = getClass.getResource("/authors.csv").toString
    val df = spark.read
      .format("csv")
      .option("header", "true")
      .load(pathToCsv)

    // Transformation
    val dfWithFullName = df
      .withColumn("fullname", concat(df("lname"), lit(", "), df("fname")))

    // Db settings
    val dbConnectionURL = "jdbc:postgresql://localhost/herson"
    val tableName = "authors"
    val prop = new Properties()
    prop.setProperty("driver", "org.postgresql.Driver")
    prop.setProperty("user", "herson")
    prop.setProperty("password", "herson")

    // Write to Db
    dfWithFullName.write.mode(SaveMode.Overwrite).jdbc(dbConnectionURL, tableName, prop)

    println("Completed!")
  }
}
