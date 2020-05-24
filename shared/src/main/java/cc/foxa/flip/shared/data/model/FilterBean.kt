package cc.foxa.flip.shared.data.model

import androidx.room.*
import cc.foxa.flip.shared.filter.Filter
import java.util.*
import kotlin.reflect.KClass

@Entity(tableName = "filter_bean",
    indices = [
        Index("id"),
        Index("rule_id")],
    foreignKeys = [ForeignKey(
        entity = Rule::class,
        parentColumns = ["id"],
        childColumns = ["rule_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)])
data class FilterBean(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var type: KClass<*>,
    var parameter: String = "",
    @ColumnInfo(name = "rule_id") var ruleId: String = ""
)