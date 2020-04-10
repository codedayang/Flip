package ink.ddddd.flip.shared.util

import ink.ddddd.flip.shared.data.model.FilterBean
import ink.ddddd.flip.shared.filter.Filter
import ink.ddddd.flip.shared.filter.NoFilter
import ink.ddddd.flip.shared.filter.TagFilter

object FilterFactory {
    fun get(bean: FilterBean): Filter {
        return when (bean.type) {
            TagFilter::class -> {
                TagFilter.fromFilterBean(bean)
            }
            NoFilter::class -> {
                NoFilter.fromFilterBean(bean)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}