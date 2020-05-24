package cc.foxa.flip.shared.domain.rule

import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.source.RuleFilterDao
import cc.foxa.flip.shared.domain.UseCase
import cc.foxa.flip.shared.util.FilterFactory
import javax.inject.Inject

class GetRules @Inject constructor(
    private val ruleFilterDao: RuleFilterDao
) : UseCase<Unit, List<Rule>>() {
    override fun execute(parameters: Unit): List<Rule> {
        val rules = ruleFilterDao.getRules()
        rules.forEach { rule ->
            rule.filters = ruleFilterDao.getFilterBeans(rule.id).map {
                FilterFactory.get(it)
            }
        }
        return rules
    }

}