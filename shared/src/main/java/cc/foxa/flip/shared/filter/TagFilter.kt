package cc.foxa.flip.shared.filter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.FilterBean
import cc.foxa.flip.shared.data.model.Tag
import java.lang.reflect.Type
import java.util.*


class TagFilter (
    override val id: String = UUID.randomUUID().toString(),
    override var ruleId: String = "",
    private var tags: List<Tag>
) : Filter(id, ruleId) {
    override fun check(card: Card): Boolean {
        tags.forEach {
            if (!card.tags.contains(it)) return false
        }
        return true
    }

    override fun toFilterBean(): FilterBean {
        val filterId = id
        val filterRuleId = ruleId
        return FilterBean(type = this::class).apply {
            this.ruleId = filterRuleId
            this.id = filterId
            parameter = Gson().toJson(tags)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("包含Tag:")
        tags.forEach {
            sb.append(it.name + ", ")
        }
        sb.deleteCharAt(sb.lastIndex)
        sb.deleteCharAt(sb.lastIndex)
        return sb.toString()
    }

    companion object {
        fun fromFilterBean(bean: FilterBean): TagFilter {
            val type: Type = object : TypeToken<List<Tag>>() {}.type
            val tagList = Gson().fromJson<List<Tag>>(bean.parameter, type)
            return TagFilter(id = bean.id, tags = tagList, ruleId = bean.ruleId)
        }
    }

}