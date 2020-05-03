package ink.ddddd.flip.shared.util

import ink.ddddd.flip.shared.data.model.FilterBean
import ink.ddddd.flip.shared.filter.Filter
import ink.ddddd.flip.shared.filter.NoFilter
import ink.ddddd.flip.shared.filter.TagFilter
import kotlin.reflect.KClass

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

    fun get(klass: KClass<out Filter>): Filter {
        return when (klass) {
            TagFilter::class -> {
                TagFilter(tags = listOf())
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}