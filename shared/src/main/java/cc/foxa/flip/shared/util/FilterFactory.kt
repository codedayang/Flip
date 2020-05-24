package cc.foxa.flip.shared.util

import cc.foxa.flip.shared.data.model.FilterBean
import cc.foxa.flip.shared.filter.*
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
            KeyWordFilter::class -> {
                KeyWordFilter.fromFilterBean(bean)
            }
            PriorityFilter::class -> {
                PriorityFilter.fromFilterBean(bean)
            }
            WithinDaysFilter::class -> {
                WithinDaysFilter.fromFilterBean(bean)
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