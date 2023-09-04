package com.tanpopo.demo.batch.tool
import org.scalatest.funsuite.AnyFunSuite
import com.tanpopo.demo.commons.util.StringUtil
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
/**
 * 测试验证
 *
 * @author tanpopo
 * @since 2023/9/4
 *
 */
@RunWith(classOf[JUnitRunner])
class OtherTest extends AnyFunSuite {
  test("testCrossModule") {
    val newStr = StringUtil.shortMD5("xxxxxxx")
    println(newStr)
  }
}
