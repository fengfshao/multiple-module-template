package com.tanpopo.demo.commons.util
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoSugar
import org.scalatest.funsuite.AnyFunSuite

/**
 * HDFSUtil单测
 *
 * @author tanpopo
 * @since 2023/6/2
 *
 */
class HDFSUtilTest extends AnyFunSuite with MockitoSugar {

  test("testGetMaxPartition") {
    withObjectMocked[HDFSUtil.type] {
      when(HDFSUtil.getPartitions(anyString, anyString)).thenReturn(Array("20230529", "20230530"))
      when(HDFSUtil.getMaxPartition(anyString,anyString)).thenCallRealMethod()
      assertResult("20230530")(HDFSUtil.getMaxPartition("", ""))
    }
  }

}
