package cc.foxa.flip.shared.filter

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.FilterBean
import com.google.gson.Gson
import java.util.*

class KeyWordFilter (
    override val id: String = UUID.randomUUID().toString(),
    override var ruleId: String = "",
    private var keyword: String
) : Filter(id, ruleId) {
    override fun check(card: Card): Boolean {
        return card.front.contains(keyword) || card.back.contains(keyword)
    }

    override fun toFilterBean(): FilterBean {
        val filterId = id
        val filterRuleId = ruleId
        return FilterBean(type = this::class).apply {
            this.ruleId = filterRuleId
            this.id = filterId
            parameter = keyword
        }
    }

    override fun toString(): String {
        return "包含关键字: $keyword"
    }

    companion object {
        fun fromFilterBean(bean: FilterBean): KeyWordFilter {
            return KeyWordFilter(id = bean.id, keyword = bean.parameter, ruleId = bean.ruleId)
        }
    }

}