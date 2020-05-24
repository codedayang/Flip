package cc.foxa.flip.shared.filter

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.FilterBean
import java.util.*

class WithinDaysFilter (
    override val id: String = UUID.randomUUID().toString(),
    override var ruleId: String = "",
    private var days: Int
) : Filter(id, ruleId) {
    override fun check(card: Card): Boolean {
        val calendar = Calendar.getInstance().apply {
            time = Date(System.currentTimeMillis())
            add(Calendar.DAY_OF_YEAR, -days)
        }
        return card.createTime.after(calendar.time)
    }

    override fun toFilterBean(): FilterBean {
        val filterId = id
        val filterRuleId = ruleId
        return FilterBean(type = this::class).apply {
            this.ruleId = filterRuleId
            this.id = filterId
            parameter = days.toString()
        }
    }

    override fun toString(): String {
        return "${days}天内添加的卡片"
    }

    companion object {
        fun fromFilterBean(bean: FilterBean): WithinDaysFilter {
            return WithinDaysFilter(id = bean.id, ruleId = bean.ruleId, days = bean.parameter.toInt())
        }
    }

}