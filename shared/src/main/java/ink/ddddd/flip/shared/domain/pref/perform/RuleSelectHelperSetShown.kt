package ink.ddddd.flip.shared.domain.pref.perform

import ink.ddddd.flip.shared.data.pref.PreferenceStorage
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class RuleSelectHelperSetShown @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.ruleSelectHelperShown = parameters
    }

}