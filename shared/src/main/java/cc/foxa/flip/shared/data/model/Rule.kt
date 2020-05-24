package cc.foxa.flip.shared.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import cc.foxa.flip.shared.filter.Filter
import java.util.*

@Entity(tableName = "rule", indices = [Index("id", unique = true)])
data class Rule(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    @Ignore var filters: List<Filter> = emptyList()
) {
    fun check(card: Card): Boolean {
        filters.forEach {
            if (!it.check(card)) return false
        }
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        filters.forEach {
            sb.append(it)
            if (filters.indexOf(it) != filters.size-1) {
                sb.append("\n")
            }
        }
        return sb.toString()
    }
}