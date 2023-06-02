package com.tanpopo.demo.batch
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * spark本地环境trait，创建local模式的SparkSession，用于单测类mixed in
 *
 * @author tanpopo
 * @since 2023/3/17
 *
 */
trait SparkTesting {
  @transient lazy val spark: SparkSession = SparkSession
    .builder()
    .config(new SparkConf())
    .config("spark.driver.bindAddress", "127.0.0.1")
    .master("local[*]")
    .appName("SparkUnitTesting")
    .getOrCreate()
}
