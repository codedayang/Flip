package ink.ddddd.flip.shared.domain.rule

import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.source.RuleFilterDao
import ink.ddddd.flip.shared.domain.UseCase
import ink.ddddd.flip.shared.util.FilterFactory
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