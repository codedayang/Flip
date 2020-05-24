package cc.foxa.flip.shared.filter

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.FilterBean

abstract class Filter(
    open val id: String,
    open val ruleId: String
) {
    abstract fun check(card: Card): Boolean
    abstract fun toFilterBean(): FilterBean
}