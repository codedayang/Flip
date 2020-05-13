package ink.ddddd.flip.shared.domain.rule

import ink.ddddd.flip.shared.data.NoSuchRuleException
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.source.RuleFilterDao
import ink.ddddd.flip.shared.domain.UseCase
import ink.ddddd.flip.shared.util.FilterFactory
import javax.inject.Inject

class GetRuleById @Inject constructor(
    private val ruleFilterDao: RuleFilterDao
): UseCase<String, Rule>() {
    override fun execute(parameters: String): Rule {
        try {
            return ruleFilterDao.getRuleById(parameters).apply {
                filters = ruleFilterDao.getFilterBeans(id).map {
                    FilterFactory.get(it)
                }
            }
        } catch (e: NullPointerException) {
            throw NoSuchRuleException()
        }

    }

}