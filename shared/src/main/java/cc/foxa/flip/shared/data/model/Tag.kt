package cc.foxa.flip.shared.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tag", indices = [Index("id", unique = true)])
data class Tag(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var name: String
)