package cc.foxa.flip.shared.domain.rule

import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.source.RuleFilterDao
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class UpdateRule @Inject constructor(
    private val ruleFilterDao: RuleFilterDao
) : UseCase<Rule, Unit>() {
    override fun execute(parameters: Rule) {
        ruleFilterDao.deleteFilterBeanByRule(parameters.id)
        ruleFilterDao.updateRule(parameters)
        parameters.filters.forEach {
            ruleFilterDao.updateFilterBean(it.toFilterBean().apply { ruleId = parameters.id })
        }
    }

}