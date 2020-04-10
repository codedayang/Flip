package ink.ddddd.flip.shared.filter

import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.FilterBean

abstract class Filter(
    open val id: String,
    open val ruleId: String
) {
    abstract fun check(card: Card): Boolean
    abstract fun toFilterBean(): FilterBean
}