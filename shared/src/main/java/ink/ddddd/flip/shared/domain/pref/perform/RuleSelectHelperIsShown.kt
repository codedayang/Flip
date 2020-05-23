package ink.ddddd.flip.shared.domain.pref.perform

import ink.ddddd.flip.shared.data.pref.PreferenceStorage
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class RuleSelectHelperIsShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean {
        return preferenceStorage.ruleSelectHelperShown
    }

}