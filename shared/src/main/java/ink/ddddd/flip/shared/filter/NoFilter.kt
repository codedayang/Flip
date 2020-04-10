package ink.ddddd.flip.shared.filter

import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.FilterBean
import java.util.*

class NoFilter (
    override val id: String = UUID.randomUUID().toString(),
    override val ruleId: String = ""
) : Filter(id, ruleId) {
    override fun check(card: Card): Boolean {
        return true
    }

    override fun toFilterBean(): FilterBean {
        return FilterBean(id = id, type = this::class, parameter = "", ruleId = ruleId)
    }

    companion object {
        fun fromFilterBean(bean: FilterBean): NoFilter {
            return NoFilter(bean.id, bean.ruleId)
        }
    }

}