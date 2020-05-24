package cc.foxa.flip.shared.util

import cc.foxa.flip.shared.data.model.FilterBean
import cc.foxa.flip.shared.filter.Filter
import cc.foxa.flip.shared.filter.NoFilter
import cc.foxa.flip.shared.filter.TagFilter
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