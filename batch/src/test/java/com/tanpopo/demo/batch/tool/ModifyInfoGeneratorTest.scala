package com.tanpopo.demo.batch.tool
import com.tanpopo.demo.batch.SparkTesting
import org.scalatest.funsuite.AnyFunSuite


/**
 *
 *
 * @author tanpopo
 * @since 2023/6/2
 *
 */
class ModifyInfoGeneratorTest extends AnyFunSuite with SparkTesting{

  test("testDiff") {
    import spark.implicits._
    val data1 = spark.sparkContext.parallelize(List(
      ExampleData1("vid111", "scene_aaa", 444, 16098989878L, "t1,t2"),
      ExampleData1("vid222", "scene_bbb", 123, 16098989833L, "t1,t3")
    )).toDF()

    val data2 = spark.sparkContext.parallelize(List(
      ExampleData2("vid222", "scene_ddd", 123, "t1,t3", "movie"),
      ExampleData2("vid333", "scene_ccc", 789, "t1,t5", "documentary")
    )).toDF()

    val changeInfoArr = ModifyInfoGenerator.diff(data1, data2, "vid", 166788999670L,"test").collect()
    assertResult(3)(changeInfoArr.length)
    assertResult(Map("new" -> "vid333", "old" -> ""))(changeInfoArr.find(_.id=="vid333").get.fieldInfos("vid"))
  }

}

case class ExampleData1(vid: String, scene: String, tagId: Int, etime: Long, topicids: String)
case class ExampleData2(vid: String, scene: String, tagId: Int, topicids: String, cidType: String)
