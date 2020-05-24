package cc.foxa.flip.shared.domain.pref.onboarding

import cc.foxa.flip.shared.data.pref.PreferenceStorage
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class SetOnBoardingState @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.onBoardingCompleted = parameters
    }

}