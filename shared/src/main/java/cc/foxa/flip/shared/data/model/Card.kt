package cc.foxa.flip.shared.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "card", indices = [Index("id", unique = true)])
data class Card(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var front: String = "",
    var back: String = "",
    var priority: Int = 5,
    var createTime: Date = Date(System.currentTimeMillis()),
    var editTime: Date = Date(System.currentTimeMillis()),
    @Ignore var tags: List<Tag> = emptyList()
)