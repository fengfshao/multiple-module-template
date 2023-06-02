package com.tanpopo.demo.commons.util

import java.io.Serializable
import java.util.concurrent.ConcurrentHashMap
import java.util.function

/**
 * main参数解析工具
 *
 * @author tanpopo
 * @since 2023/5/29
 */
class ArgumentsUtil(args: Array[String]) extends Serializable {
  val argsMap: Map[String, String] = parseArgs(args)

  /**
   * KV格式参数解析，格式要求：key=value，等号左右可以含空格
   *
   * @param args 参数数组
   * @return 若参数为空返回null，否则返回(key,value)格式的不可变map
   */
  def parseArgs(args: Array[String]): Map[String, String] = {
    val argsMap = scala.collection.mutable.Map.empty[String, String]
    if (args != null && args.length > 0) args.iterator.map(item => item.split("=", -1))
        .filter(array => if (array.length == 2) true else false)
        .foreach(item => argsMap.put(item(0).trim, item(1).trim))
    argsMap.toMap
  }

  /**
   * 获取String类型参数值
   */
  def getString(key: String): String = if (argsMap.contains(key)) argsMap(key) else throw new Exception(s"[args error] $key is not available! ")

  /**
   * 获取String类型参数值，可以设置默认值
   */
  def getString(key: String, defaultValue: String): String = argsMap.getOrElse(key, defaultValue)

  /**
   * 获取Int类型参数值
   */
  def getInt(key: String): Int = if (argsMap.contains(key)) argsMap(key).toInt else throw new Exception(s"[args error] $key is not available! ")

  /**
   * 获取Int类型参数值，可以设置默认值
   */
  def getInt(key: String, defaultValue: Int): Int = {
    val value  = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) try value.toInt catch {
        case _: Exception => defaultValue
      } else defaultValue
  }

  /**
   * 获取Float类型参数值
   */
  def getFloat(key: String): Float = if (argsMap.contains(key)) argsMap(key).toFloat else throw new Exception(s"[args error] $key is not available! ")

  /**
   * 获取Float类型参数值，可以设置默认值
   */
  def getFloat(key: String, defaultValue: Float): Float = {
    val value  = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) try value.toFloat catch {
        case _: Exception => defaultValue
      } else defaultValue
  }

  /**
   * 获取Boolean类型参数值
   */
  def getBoolean(key: String): Boolean = if (argsMap.contains(key)) argsMap(key).toBoolean else throw new Exception(s"[args error] $key is not available! ")

  /**
   * 获取Boolean类型参数值，可以设置默认值
   */
  def getBoolean(key: String, defaultValue: Boolean): Boolean = {
    val value  = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) try value.toBoolean catch {
        case _: Exception => defaultValue
      } else defaultValue
  }

  /**
   * 获取Double类型参数值
   */
  def getDouble(key: String): Double = if (argsMap.contains(key)) argsMap(key).toDouble else throw new Exception(s"[args error] $key is not available! ")

  /**
   * 获取Double类型参数值，可以设置默认值
   */
  def getDouble(key: String, default: Double): Double = {
    val value  = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) try value.toDouble catch {
        case _: Exception => default
      } else default
  }

  def printArgsMap(): Unit = {
    argsMap.foreach(entry => {
      println(s"[INFO] ${entry._1}: ${entry._2}")
    })
  }
}

object ArgumentsUtil {

  private val cache: java.util.Map[Int, ArgumentsUtil] = new ConcurrentHashMap[Int, ArgumentsUtil]()

  /**
   * 获取ArgumentsUtil实例
   */
  def getInstance(args: Array[String]): ArgumentsUtil = {
    val hashcode: Int = args.mkString("#").hashCode
    cache.computeIfAbsent(hashcode, new function.Function[Int, ArgumentsUtil] {
      override def apply(t: Int): ArgumentsUtil = new ArgumentsUtil(args)
    })
  }

  /**
   * KV格式参数解析，格式要求：key=value，等号左右可以含空格
   *
   * @param args 参数数组
   * @return 若参数为空返回null，否则返回(key,value)格式的不可变map
   */
  def kvParse(args: Array[String]): Map[String, String] = {
    val argsMap = scala.collection.mutable.Map.empty[String, String]
    if (args != null && args.length > 0) {
      args.iterator.map(item => item.split("=", -1))
        .filter(array => if (array.length == 2) true else false)
        .foreach(item => argsMap.put(item(0).trim, item(1).trim))
    }
    argsMap.toMap
  }

  /**
   * 获取String类型参数值
   */
  def getString(argsMap: Map[String, String], key: String): String = {
    if (argsMap.contains(key)) {
      argsMap(key)
    } else {
      throw new Exception(s"[args error] $key is not available! ")
    }
  }

  /**
   * 获取String类型参数值，可以设置默认值
   */
  def getString(argsMap: Map[String, String], key: String, defaultValue: String): String = {
    argsMap.getOrElse(key, defaultValue)
  }

  /**
   * 获取Int类型参数值
   */
  def getInt(argsMap: Map[String, String], key: String): Int = {
    if (argsMap.contains(key)) {
      argsMap(key).toInt
    } else {
      throw new Exception(s"[args error] $key is not available! ")
    }
  }

  /**
   * 获取Int类型参数值，可以设置默认值
   */
  def getInt(argsMap: Map[String, String], key: String, defaultValue: Int): Int = {
    val value: String = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) {
      try {
        value.toInt
      } catch {
        case _: Exception => defaultValue
      }
    } else {
      defaultValue
    }
  }

  /**
   * 获取Float类型参数值
   */
  def getFloat(argsMap: Map[String, String], key: String): Float = {
    if (argsMap.contains(key)) {
      argsMap(key).toFloat
    } else {
      throw new Exception(s"[args error] $key is not available! ")
    }
  }

  /**
   * 获取Float类型参数值，可以设置默认值
   */
  def getFloat(argsMap: Map[String, String], key: String, defaultValue: Float): Float = {
    val value: String = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) {
      try {
        value.toFloat
      } catch {
        case _: Exception => defaultValue
      }
    } else {
      defaultValue
    }
  }

  /**
   * 获取Boolean类型参数值
   */
  def getBoolean(argsMap: Map[String, String], key: String): Boolean = {
    if (argsMap.contains(key)) {
      argsMap(key).toBoolean
    } else {
      throw new Exception(s"[args error] $key is not available! ")
    }
  }

  /**
   * 获取Boolean类型参数值，可以设置默认值
   */
  def getBoolean(argsMap: Map[String, String], key: String, defaultValue: Boolean): Boolean = {
    val value: String = argsMap.getOrElse(key, "")
    if (!StringUtil.isNullOrEmpty(value)) {
      try {
        value.toBoolean
      } catch {
        case _: Exception => defaultValue
      }
    } else {
      defaultValue
    }
  }
}