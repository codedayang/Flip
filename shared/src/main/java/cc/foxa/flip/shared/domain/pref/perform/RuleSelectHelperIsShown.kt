package cc.foxa.flip.shared.domain.pref.perform

import cc.foxa.flip.shared.data.pref.PreferenceStorage
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class RuleSelectHelperIsShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean {
        return preferenceStorage.ruleSelectHelperShown
    }

}