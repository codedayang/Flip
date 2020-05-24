package cc.foxa.flip.shared.domain.rule

import cc.foxa.flip.shared.data.NoSuchRuleException
import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.source.RuleFilterDao
import cc.foxa.flip.shared.domain.UseCase
import cc.foxa.flip.shared.util.FilterFactory
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