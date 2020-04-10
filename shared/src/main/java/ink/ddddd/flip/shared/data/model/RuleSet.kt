package ink.ddddd.flip.shared.data.model

import java.util.*

/**
    Warp rules
 */
data class RuleSet(
    var id: String = UUID.randomUUID().toString(),
    var isUnion: Boolean = true,
    var rules: List<Rule>
) {
    fun check(card: Card): Boolean {
        if (isUnion) {
            rules.forEach {
                if (it.check(card)) return true
            }
            return false
        } else {
            rules.forEach {
                if (!it.check(card)) return false
            }
            return true
        }
    }
}