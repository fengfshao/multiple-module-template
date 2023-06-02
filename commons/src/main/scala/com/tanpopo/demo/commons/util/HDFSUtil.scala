package com.tanpopo.demo.commons.util

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._

import java.net.URI

object HDFSUtil {

  /**
   * 删除指定hdfs路径
   *
   * @param path 要删除的路径
   */
  def deleteIfExist(path: String): Boolean = {
    try {
      val hdfs = FileSystem.get(URI.create(path), new Configuration)
      //检查output目录是否已存在，存在则删除，便于重跑
      val outputPath = new Path(path)
      if (hdfs.exists(outputPath)) {
        hdfs.delete(outputPath, true)
      }
    } catch {
      case e: Exception => throw new Exception(e)
    }
    true
  }

  /**
   * 判断hdfs路径是否存在
   *
   * @param path 目标路径
   * @return 存在返回true，否则返回false
   */
  def isExist(path: String): Boolean = {
    val hdfs = FileSystem.get(URI.create(path), new Configuration)
    val filePath = new Path(path)
    hdfs.exists(filePath)
  }

  /**
   * 获取hdfs路径下的最大可用分区
   *
   * @param srcPath       root path
   * @param checkFileName check文件名，完全匹配
   * @return 最大可用分区，若没有可用分区，返回空
   */
  def getMaxPartition(srcPath: String, checkFileName: String): String = {
    getPartitions(srcPath, checkFileName).max
  }

  def getPartitions(srcPath: String, checkFileName: String): Array[String] = {
    val hdfs = FileSystem.get(URI.create(srcPath), new Configuration)
    val rootPath = new Path(srcPath)
    if (hdfs.exists(rootPath)) {
      val fileStatusArray = hdfs.listStatus(rootPath)
      fileStatusArray.flatMap { fileStatus =>
        val curPartition = fileStatus.getPath.getName
        if (fileStatus.isDirectory) {
          if (!StringUtil.isNullOrEmpty(checkFileName)) {
            val checkFilePath = new Path(s"$srcPath/$curPartition/$checkFileName")
            if (hdfs.exists(checkFilePath)) {
              Some(curPartition)
            } else {
              None
            }
          } else {
            Some(curPartition)
          }
        } else {
          None
        }
      }
    } else {
      Array.empty[String]
    }
  }
}
