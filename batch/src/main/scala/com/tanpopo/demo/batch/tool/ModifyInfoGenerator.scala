package com.tanpopo.demo.batch.tool
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row}

/**
 * 比较两份数据，产生变更delta
 * 比较两个dataframe，将其差异部分转为变更消息格式数据
 *
 * 示例变更消息如下：
 *
 * action : 0：新增，2：删除，1：字段变更
 * -- {
 * --- "id":"vid222",
 * --- "pushTimeStamp":166788999670,
 * --- "source":"xx",
 * --- "fieldInfos":{
 * ------ "etime":{
 * --------  "old":16098989833
 * --------  "new":""
 * ------  },
 * ------ "cidType":{
 * --------  "new":"movie"
 * --------  "old":""
 * ------ },
 * ------ "scene":{
 * --------  "old":"scene_bbb",
 * --------  "new":"scene_ddd"
 * ------ }
 * ---- },
 * --- "action":1
 * -- }
 */
object ModifyInfoGenerator {

  def diff(
      last: DataFrame,
      current: DataFrame,
      keyField: String,
      currentDataTime: Long,
      source: String): RDD[ChangeInfo] = {
    val pairLast: RDD[(String, Row)] = last.rdd.map(r => (String.valueOf(r.getAs[Any](keyField)), r))
    val pairCurrent: RDD[(String, Row)] = current.rdd.map(r => (String.valueOf(r.getAs[Any](keyField)), r))

    val joinData = pairLast
      .fullOuterJoin(pairCurrent)
      .map { p: (String, (Option[Row], Option[Row])) =>
        val key = p._1
        val lastRowOption = p._2._1
        val currentRowOption = p._2._2
        if (lastRowOption.isEmpty) {
          // increase row
          val currentRow = currentRowOption.get
          val newFields = currentRow.getValuesMap[Any](currentRow.schema.fieldNames)
          val modifyInfo = newFields.map(p => p._1 -> Map("new" -> p._2, "old" -> ""))
          ChangeInfo(key, currentDataTime, modifyInfo, 0, source)
        } else if (currentRowOption.isEmpty) {
          // delete row
          val lastRow = lastRowOption.get
          val oldFields = lastRow.getValuesMap[Any](lastRow.schema.fieldNames)
          val modifyInfo = oldFields.map(p => p._1 -> Map("old" -> p._2, "new" -> ""))
          ChangeInfo(key, currentDataTime, modifyInfo, 2, source)
        } else {
          // update row
          val lastRow = lastRowOption.get
          val currentRow = currentRowOption.get
          val oldFields = lastRow.getValuesMap[Any](lastRow.schema.fieldNames)
          val newFields = currentRow.getValuesMap[Any](currentRow.schema.fieldNames)
          // fields only exist in old
          val deletedFieldNames: List[String] = oldFields.keySet.diff(newFields.keySet).toList
          val deletedFields: List[Map[String, Any]] = deletedFieldNames.map { name =>
            Map("old" -> oldFields(name), "new" -> "")
          }
          // fields only exist in new
          val increasedFieldNames: List[String] = newFields.keySet.diff(oldFields.keySet).toList
          val increasedFields: List[Map[String, Any]] = increasedFieldNames.map { name =>
            Map("new" -> newFields(name), "old" -> "")
          }
          // fields to diff
          val diffFieldNames = oldFields.keySet.&(newFields.keySet).toList
          val updatedFieldNames = diffFieldNames.filterNot { name =>
            // null value may exist in fieldsMap
            val oldValue: Any = Option(oldFields(name)).getOrElse("")
            val newValue: Any = Option(newFields(name)).getOrElse("")
            java.util.Objects.equals(oldValue, newValue)
          }
          val updatedFields: List[Map[String, Any]] = updatedFieldNames.map { name =>
            Map("old" -> oldFields(name), "new" -> newFields(name))
          }
          val modifyInfo: Map[String, Map[String, Any]] = deletedFieldNames
            .zip(deletedFields)
            .union(increasedFieldNames.zip(increasedFields))
            .union(updatedFieldNames.zip(updatedFields))
            .toMap

          ChangeInfo(key, currentDataTime, modifyInfo, 1, source)
        }
      }
      .filter(_.fieldInfos.nonEmpty)
    joinData
  }
}

/**
 *
 * @param id            key
 * @param pushTimeStamp 更新时间
 * @param fieldInfos    字段变更信息，使用new描述新值，old描述旧值例如：
 *                      更新字段field1，Map("field1",Map("new":"v1","old":"v0"))
 *                      新增字段field1，此时只有new值，Map("field1",Map("new":"v1"))
 *                      删除字段field1，此时只有old值，Map("field1",Map("old":"v0"))
 * @param action        行更新状态 0->increase新增行, 1->update更新行, 2->delete删除行
 * @param source        diff的源头
 */
case class ChangeInfo(
    id: String,
    pushTimeStamp: Long,
    fieldInfos: Map[String, Map[String, Any]],
    action: Int,
    source: String)
