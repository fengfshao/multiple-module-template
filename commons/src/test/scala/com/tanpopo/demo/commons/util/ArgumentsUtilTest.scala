package com.tanpopo.demo.commons.util
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

/**
 * ArgumentsUtil单测
 *
 * @author tanpopo
 * @since 2023/5/29
 *
 */
@RunWith(classOf[JUnitRunner])
class ArgumentsUtilTest extends AnyFunSuite{
  test("testGetDouble") {
    val arr = new ArgumentsUtil(Array("jobName=test", "interval=3"))
    assertResult("test")(arr.getString("jobName"))
    assertResult(3)(arr.getInt("interval"))
    assertThrows[Exception](arr.getInt("key3"))
  }


  test("testGetString") {
    val arr = new ArgumentsUtil(Array("jobTime=2021121500"))
    assertResult("2021121500")(arr.getString("jobTime"))
  }

  test("testGetInt") {
    val arr = new ArgumentsUtil(Array("length=31"))
    assertResult(31)(arr.getInt("length"))
  }

  test("testGetBoolean") {
    val arr = new ArgumentsUtil(Array("enable=true"))
    assertResult(true)(arr.getBoolean("enable"))
  }
}
