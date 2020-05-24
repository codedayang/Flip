package cc.foxa.flip.shared.filter

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.FilterBean
import java.util.*

class PriorityFilter (
    override val id: String = UUID.randomUUID().toString(),
    override var ruleId: String = "",
    private var fromPriority: Int,
    private var toPriority: Int
) : Filter(id, ruleId) {
    override fun check(card: Card): Boolean {
        return card.priority in fromPriority..toPriority
    }

    override fun toFilterBean(): FilterBean {
        val filterId = id
        val filterRuleId = ruleId
        return FilterBean(type = this::class).apply {
            this.ruleId = filterRuleId
            this.id = filterId
            parameter = "$fromPriority-$toPriority"
        }
    }

    override fun toString(): String {
        return "权值范围: $fromPriority~$toPriority"
    }

    companion object {
        fun fromFilterBean(bean: FilterBean): PriorityFilter {
            val from = bean.parameter.split('-')[0].toInt()
            val to = bean.parameter.split('-')[1].toInt()
            return PriorityFilter(id = bean.id, ruleId = bean.ruleId, fromPriority = from, toPriority = to)
        }
    }

}